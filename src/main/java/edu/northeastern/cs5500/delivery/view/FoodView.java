package edu.northeastern.cs5500.delivery.view;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.delivery.JsonTransformer;
import edu.northeastern.cs5500.delivery.controller.FoodController;
import edu.northeastern.cs5500.delivery.model.Food;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class FoodView implements View {

    @Inject
    FoodView() {}

    @Inject JsonTransformer jsonTransformer;

    @Inject FoodController foodController;

    @Override
    public void register() {
        log.info("FoodView > register");

        get(
                "/food",
                (request, response) -> {
                    log.debug("/food");
                    response.type("application/json");
                    return foodController.getFoods();
                },
                jsonTransformer);

        get(
                "/food/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    log.debug("/food/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    Food food = foodController.getFood(id);
                    if (food == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    return food;
                },
                jsonTransformer);

        post(
                "/food",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Food food = mapper.readValue(request.body(), Food.class);
                    if (!food.isValid()) {
                        response.status(400);
                        return "";
                    }

                    // Ignore the user-provided ID if there is one
                    food.setId(null);
                    food = foodController.addFood(food);

                    response.redirect(String.format("/food/{}", food.getId().toHexString()), 301);
                    return food;
                });

        put(
                "/food",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Food food = mapper.readValue(request.body(), Food.class);
                    if (!food.isValid()) {
                        response.status(400);
                        return "";
                    }

                    foodController.updateFood(food);
                    return food;
                });

        delete(
                "/food",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Food food = mapper.readValue(request.body(), Food.class);

                    foodController.deleteFood(food.getId());
                    return food;
                });
    }
}
