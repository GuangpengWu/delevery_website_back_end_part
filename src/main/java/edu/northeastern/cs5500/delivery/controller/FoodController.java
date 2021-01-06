package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Food Controller
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class FoodController {
    private final GenericRepository<Food> foods;

    /**
     * constructor
     *
     * @param foodRepository food Repository
     */
    @Inject
    FoodController(GenericRepository<Food> foodRepository) {
        foods = foodRepository;

        log.info("FoodController > construct");

        if (foods.count() > 0) {
            return;
        }

        log.info("FoodController > construct > adding default Foods");

        final Food defaultFood1 = new Food();
        defaultFood1.setName("Food first");
        defaultFood1.setPrice(5);

        final Food defaultFood2 = new Food();
        defaultFood2.setName("Food second");
        defaultFood2.setDescription("traditional Franch food");
        defaultFood2.setPrice(10);

        try {
            addFood(defaultFood1);
            addFood(defaultFood2);
        } catch (Exception e) {
            log.error("FoodController > construct > adding default Foods > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get food by id
     *
     * @param uuid food id
     * @return food get by id
     */
    @Nullable
    public Food getFood(@Nonnull ObjectId uuid) {
        log.debug("FoodController > getFood({})", uuid);
        return foods.get(uuid);
    }

    /** Get all foods from DB */
    @Nonnull
    public Collection<Food> getFoods() {
        log.debug("FoodController > getFoods()");
        return foods.getAll();
    }

    /**
     * Add food to DB
     *
     * @param food food to added
     * @return return food added
     * @throws Exception when food is invalid or food has already existed
     */
    @Nonnull
    public Food addFood(@Nonnull Food food) throws Exception {
        log.debug("FoodController > addFood(...)");
        if (!food.isValid()) {
            throw new Exception("InvalidFoodException");
        }

        ObjectId id = food.getId();

        if (id != null && foods.get(id) != null) {
            throw new Exception("DuplicateKeyException");
        }

        return foods.add(food);
    }

    /**
     * Update food from DB
     *
     * @param food new customer with updated information
     * @throws Exception when food is invalid
     */
    public void updateFood(@Nonnull Food food) throws Exception {
        log.debug("FoodController > updateFood(...)");
        foods.update(food);
    }

    /**
     * Delete customer from DB by id
     *
     * @param id customer id
     * @throws Exception when food is invalid
     */
    public void deleteFood(@Nonnull ObjectId id) throws Exception {
        log.debug("FoodController > deleteFood(...)");
        foods.delete(id);
    }

    /**
     * Get foods by cart
     *
     * @param cart shopping cart
     * @return foods in this cart
     */
    public ArrayList<Food> getFoodsFromCart(@Nonnull ShoppingCart cart) {
        ArrayList<Food> foodList = new ArrayList<>();
        for (String foodId : cart.getItems().keySet()) {
            foodList.add(this.foods.get(new ObjectId(foodId)));
        }
        return foodList;
    }
}
