package edu.northeastern.cs5500.delivery.view;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.delivery.JsonTransformer;
import edu.northeastern.cs5500.delivery.controller.CustomerController;
import edu.northeastern.cs5500.delivery.controller.DeliveryController;
import edu.northeastern.cs5500.delivery.controller.FoodController;
import edu.northeastern.cs5500.delivery.controller.RestaurantController;
import edu.northeastern.cs5500.delivery.controller.ShoppingCartController;
import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.OrderItem;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class DeliveryView implements View {

    @Inject
    DeliveryView() {}

    @Inject JsonTransformer jsonTransformer;
    @Inject DeliveryController deliveryController;
    @Inject CustomerController customerController;
    @Inject ShoppingCartController shoppingCartController;
    @Inject FoodController foodController;

    @Inject RestaurantController restaurantController;

    @Override
    public void register() {
        log.info("DeliveryView > register");

        get(
                "/delivery",
                (request, response) -> {
                    log.debug("/delivery");
                    response.type("application/json");
                    return deliveryController.getDeliverys();
                },
                jsonTransformer);

        get(
                "/delivery/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    log.debug("/delivery/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    Delivery delivery = deliveryController.getDelivery(id);
                    if (delivery == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    return delivery;
                },
                jsonTransformer);

        post(
                "/placeOrder",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> map =
                            mapper.readValue(
                                    request.body(), new TypeReference<Map<String, String>>() {});
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    }
                    ObjectId shoppingCartId = new ObjectId(map.get("shoppingCartId"));
                    ObjectId customerId = new ObjectId(map.get("customerId"));
                    ObjectId restaurantId = new ObjectId(map.get("restaurantId"));
                    String address = map.get("address");
                    String phone = map.get("phone");
                    String instruction = map.get("instruction");
                    List<OrderItem> orderItems = new ArrayList<>();
                    // Check if there is a valid and existing customer bonded to this delivery.
                    ShoppingCart cart = shoppingCartController.getShoppingCart(shoppingCartId);
                    if (customerController.getCustomer(customerId) == null || cart == null) {
                        response.status(400);
                        return "";
                    }
                    // Construct the orderItems list.
                    for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
                        Food f = foodController.getFood(new ObjectId(entry.getKey()));
                        orderItems.add(
                                new OrderItem(
                                        f.getId(), f.getName(), f.getPrice(), entry.getValue()));
                    }
                    // TODO: restaurantName is set to default value for now.
                    Delivery delivery =
                            deliveryController.addDelivery(
                                    new Delivery(
                                            address,
                                            phone,
                                            instruction,
                                            customerId,
                                            restaurantId,
                                            "restaurant name",
                                            orderItems));

                    shoppingCartController.empty(shoppingCartId);
                    System.out.println(delivery.toString());
                    return delivery;
                },
                jsonTransformer);

        put(
                "/delivery",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Delivery delivery = mapper.readValue(request.body(), Delivery.class);
                    if (!delivery.isValid()) {
                        response.status(400);
                        return "";
                    }

                    deliveryController.updateDelivery(delivery);
                    return delivery;
                });

        delete(
                "/delivery",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Delivery delivery = mapper.readValue(request.body(), Delivery.class);

                    deliveryController.deleteDelivery(delivery.getId());
                    return delivery;
                });
    }
}
