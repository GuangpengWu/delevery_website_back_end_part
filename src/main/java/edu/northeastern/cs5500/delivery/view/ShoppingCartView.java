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
import edu.northeastern.cs5500.delivery.controller.FoodController;
import edu.northeastern.cs5500.delivery.controller.ShoppingCartController;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class ShoppingCartView implements View {

    @Inject
    ShoppingCartView() {}

    @Inject JsonTransformer jsonTransformer;

    @Inject ShoppingCartController shoppingCartController;
    @Inject CustomerController customerController;
    @Inject FoodController foodController;

    @Override
    public void register() {
        log.info("ShoppingCartView > register");

        get(
                "/shoppingCart",
                (request, response) -> {
                    log.debug("/shoppingCart");
                    response.type("application/json");
                    return shoppingCartController.getShoppingCarts();
                },
                jsonTransformer);

        get(
                "/shoppingCart/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");

                    log.debug("/shoppingCart/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    ShoppingCart shoppingCart = shoppingCartController.getShoppingCart(id);
                    if (shoppingCart == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    String shoppingCartJson = jsonTransformer.render(shoppingCart);
                    if (shoppingCart.getItems().size() == 0) return "[" + shoppingCartJson + "]";
                    String foodDetails =
                            jsonTransformer.render(foodController.getFoodsFromCart(shoppingCart));
                    System.out.println(
                            shoppingCartJson
                                    + foodDetails
                                            .replace('[', ',')
                                            .substring(0, foodDetails.length() - 2));
                    return "["
                            + shoppingCartJson
                            + foodDetails.replace('[', ',').substring(0, foodDetails.length() - 2)
                            + "]";
                });

        post(
                "/shoppingCart/addItem",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, ObjectId> map =
                            mapper.readValue(
                                    request.body(), new TypeReference<Map<String, ObjectId>>() {});
                    ObjectId foodId = map.get("foodId");
                    ObjectId shoppingCartId = map.get("shoppingCartId");
                    if (foodId == null || shoppingCartId == null) {
                        response.status(400);
                        return "";
                    }
                    return shoppingCartController.addItem(shoppingCartId, foodId);
                },
                jsonTransformer);

        post(
                "/shoppingCart/deleteItem",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, ObjectId> map =
                            mapper.readValue(
                                    request.body(), new TypeReference<Map<String, ObjectId>>() {});
                    ObjectId foodId = map.get("foodId");
                    ObjectId cartId = map.get("shoppingCartId");

                    if (foodId == null || cartId == null) {
                        response.status(400);
                        return "";
                    }

                    return shoppingCartController.deleteItem(cartId, foodId);
                },
                jsonTransformer);

        post(
                "/shoppingCart",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    ShoppingCart shoppingCart =
                            mapper.readValue(request.body(), ShoppingCart.class);

                    if (!shoppingCart.isValid()) {
                        response.status(400);
                        return "";
                    }

                    shoppingCart.setId(null);
                    shoppingCart = shoppingCartController.addShoppingCart(shoppingCart);

                    response.redirect(
                            String.format("/shoppingCart/{}", shoppingCart.getId().toHexString()),
                            301);
                    return shoppingCart;
                });

        put(
                "/shoppingCart",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    ShoppingCart shoppingCart =
                            mapper.readValue(request.body(), ShoppingCart.class);
                    if (!shoppingCart.isValid()) {
                        response.status(400);
                        return "";
                    }

                    shoppingCartController.updateShoppingCart(shoppingCart);
                    return shoppingCart;
                });

        delete(
                "/shoppingCart",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    ShoppingCart shoppingCart =
                            mapper.readValue(request.body(), ShoppingCart.class);

                    shoppingCartController.deleteShoppingCart(shoppingCart.getId());
                    return shoppingCart;
                });
    }
}
