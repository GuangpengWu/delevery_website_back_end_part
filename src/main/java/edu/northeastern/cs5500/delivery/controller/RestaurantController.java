package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.Restaurant;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Restaurant controller
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class RestaurantController {
    private final GenericRepository<Restaurant> restaurants;
    private final GenericRepository<Food> foods;

    /**
     * constructor
     *
     * @param restaurantRepository restaurant Repository
     * @param foodRepository food Repository
     */
    @Inject
    RestaurantController(
            GenericRepository<Restaurant> restaurantRepository,
            GenericRepository<Food> foodRepository) {
        foods = foodRepository;
        restaurants = restaurantRepository;

        log.info("MenuController > construct");

        if (restaurants.count() > 0) {
            return;
        }

        log.info("MenuController > construct > adding default menus");
        // restaurant 1
        final Restaurant defaultRestaurant1 = new Restaurant();
        // add 4 foods for restaurant 1
        List<Food> list1 = new ArrayList<>();
        Food food1 = new Food();
        food1.setName("Big Mac Meal");
        food1.setId(new ObjectId());
        food1.setDescription("560 - 1120 Cal.");
        food1.setPrice(1273);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list1.add(food1);

        Food food2 = new Food();
        food2.setName("10 pieces McNuggets");
        food2.setId(new ObjectId());
        food2.setDescription(
                "100% white-meat chicken breaded and marinated in our unique, fiery blend of peppers and spices.");
        food2.setPrice(422);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list1.add(food2);

        Food food3 = new Food();
        food3.setName("Iced Caramel Macchiato");
        food3.setId(new ObjectId());
        food3.setDescription(
                "McCafé® Cappuccino is made with whole or nonfat steamed milk, bold espresso made from sustainably sourced beans, foamed milk, and your choice of flavor.");
        food3.setPrice(239);
        food3.setImg("image_" + food3.getId().toHexString() + ".jpg");
        try {
            foods.add(food3);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list1.add(food3);

        Food food4 = new Food();
        food4.setName("Sausage Burrito");
        food4.setId(new ObjectId());
        food4.setDescription(
                "The Sausage Burrito is McDonald's Breakfast Burrito and is loaded with fluffy scrambled egg, pork sausage, melty cheese, green chiles and onion!");
        food4.setPrice(429);
        food4.setImg("image_" + food4.getId().toHexString() + ".jpg");
        try {
            foods.add(food4);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list1.add(food4);

        defaultRestaurant1.setName("McDonald's");
        defaultRestaurant1.setRating(4.7);
        defaultRestaurant1.setFoods(list1);
        defaultRestaurant1.setId(new ObjectId());
        defaultRestaurant1.setImg("image_" + defaultRestaurant1.getId().toHexString() + ".jpg");
        defaultRestaurant1.setThumbnail(
                "thumbnail_" + defaultRestaurant1.getId().toHexString() + ".jpg");

        // restaurant 2
        final Restaurant defaultRestaurant2 = new Restaurant();
        // add 4 foods for restaurant 2
        List<Food> list2 = new ArrayList<>();
        food1 = new Food();
        food1.setName("Kung Pao Chicken");
        food1.setId(new ObjectId());
        food1.setPrice(555);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list2.add(food1);

        food2 = new Food();
        food2.setName("Grilled Teriyaki Chicken");
        food2.setId(new ObjectId());
        food2.setDescription(
                "Grilled chicken thigh hand-sliced to order and served with teriyaki sauce.");
        food2.setPrice(1020);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list2.add(food2);

        food3 = new Food();
        food3.setName("Broccoli Beef ");
        food3.setId(new ObjectId());
        food3.setDescription(
                "A classic favorite. Tender beef and fresh broccoli in a ginger soy sauce.");
        food3.setPrice(239);
        food3.setImg("image_" + food3.getId().toHexString() + ".jpg");
        try {
            foods.add(food3);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list2.add(food3);

        food4 = new Food();
        food4.setName("Honey Walnut Shrimp");
        food4.setId(new ObjectId());
        food4.setDescription(
                "Large tempura-battered shrimp, wok-tossed in a honey sauce and topped with glazed walnuts.");
        food4.setPrice(1395);
        food4.setImg("image_" + food4.getId().toHexString() + ".jpg");
        try {
            foods.add(food4);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list2.add(food4);

        defaultRestaurant2.setName("Panda Express");
        defaultRestaurant2.setRating(4.5);
        defaultRestaurant2.setFoods(list2);
        defaultRestaurant2.setId(new ObjectId());
        defaultRestaurant2.setImg("image_" + defaultRestaurant2.getId().toHexString() + ".jpg");
        defaultRestaurant2.setThumbnail(
                "thumbnail_" + defaultRestaurant2.getId().toHexString() + ".jpg");

        // restaurant 3
        final Restaurant defaultRestaurant3 = new Restaurant();
        // add 4 foods for restaurant
        List<Food> list3 = new ArrayList<>();
        food1 = new Food();
        food1.setName("New England Clam Chowder");
        food1.setId(new ObjectId());
        food1.setDescription(
                "Meaty clams flavored with bacon, cream and a secret blend of herbs and spices. Inducted into the great chowder cook-off hall of fame. Contain pork.");
        food1.setPrice(795);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list3.add(food1);

        food2 = new Food();
        food2.setName("Seafood Bisque");
        food2.setId(new ObjectId());
        food2.setDescription(
                "Delectable medley of Pacific cod, Northwest salmon, Oregon bay shrimp, flavored with fresh basil and simmered in a creamy tomato-based broth.");
        food2.setPrice(795);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list3.add(food2);

        food3 = new Food();
        food3.setName("Crab & Oyster Chowder");
        food3.setId(new ObjectId());
        food3.setDescription(
                "Perfect balance of crabmeat and oysters, with a hint of spicy, earthy chorizo sausage, flavored with leeks, potatoes, tomatoes, and special seasonings.");
        food3.setPrice(795);
        food3.setImg("image_" + food3.getId().toHexString() + ".jpg");
        try {
            foods.add(food3);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list3.add(food3);

        food4 = new Food();
        food4.setName("Smoked Salmon Chowder");
        food4.setId(new ObjectId());
        food4.setDescription(
                "Authentic Pacific Northwest flavors, with nova smoked salmon, capers, and cream cheese.");
        food4.setPrice(795);
        food4.setImg("image_" + food4.getId().toHexString() + ".jpg");
        try {
            foods.add(food4);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list3.add(food4);

        defaultRestaurant3.setName("Pike Place Chowder");
        defaultRestaurant3.setRating(4.5);
        defaultRestaurant3.setFoods(list3);
        defaultRestaurant3.setId(new ObjectId());
        defaultRestaurant3.setImg("image_" + defaultRestaurant3.getId().toHexString() + ".jpg");
        defaultRestaurant3.setThumbnail(
                "thumbnail_" + defaultRestaurant3.getId().toHexString() + ".jpg");

        // restaurant 4
        final Restaurant defaultRestaurant4 = new Restaurant();
        // add 4 foods for restaurant 4
        List<Food> list4 = new ArrayList<>();
        food1 = new Food();
        food1.setName("Metropolitan Grill Steak Salad");
        food1.setId(new ObjectId());
        food1.setDescription(
                "Mesquite grilled American Wagyu butcher cut, mixed greens, pear tomatoes, Parmesan-peppercorn dressing, Point Reyes blue cheese, cherry radishes and red onion.");
        food1.setPrice(3000);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list4.add(food1);

        food2 = new Food();
        food2.setName("Dungeness Crab and Prawn Fettuccine");
        food2.setId(new ObjectId());
        food2.setDescription(
                "Fire-roasted tiger prawns scampi, Dungeness crab fry legs, red onions, wild mushrooms, Parmigiano Reggiano, Alfredo and herbed breadcrumbs");
        food2.setPrice(4000);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list4.add(food2);

        food3 = new Food();
        food3.setName("Vegan Bolognese (Vegan)");
        food3.setId(new ObjectId());
        food3.setDescription(
                "Plant-based meat substitute, San Marzano tomato sauce, plant-based mozzarella, penne pasta and fresh basil. *100% vegan dish.");
        food3.setPrice(795);
        food3.setImg("image_" + food3.getId().toHexString() + ".jpg");
        try {
            foods.add(food3);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list4.add(food3);

        food4 = new Food();
        food4.setName("New York Strip Loin -12oz.");
        food4.setId(new ObjectId());
        food4.setDescription(
                "Temperature Rare, Medium Rare, Medium, Medium Well, Well Done. Choice of Mashed Potatoes, Steak House Fries, Baked Potato");
        food4.setPrice(6200);
        food4.setImg("image_" + food4.getId().toHexString() + ".jpg");
        try {
            foods.add(food4);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list4.add(food4);

        defaultRestaurant4.setName("Metropolitan Grill");
        defaultRestaurant4.setRating(4.2);
        defaultRestaurant4.setFoods(list4);
        defaultRestaurant4.setId(new ObjectId());
        defaultRestaurant4.setImg("image_" + defaultRestaurant4.getId().toHexString() + ".jpg");
        defaultRestaurant4.setThumbnail(
                "thumbnail_" + defaultRestaurant4.getId().toHexString() + ".jpg");

        // restaurant 5
        final Restaurant defaultRestaurant5 = new Restaurant();
        // add 4 foods for restaurant 5
        List<Food> list5 = new ArrayList<>();
        food1 = new Food();
        food1.setName("Aguas Frescas");
        food1.setId(new ObjectId());
        food1.setDescription("Horchata or jamaica.");
        food1.setPrice(360);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list5.add(food1);

        food2 = new Food();
        food2.setName("Quesadilla");
        food2.setId(new ObjectId());
        food2.setDescription(
                "Your choice of meat or veggie, melted cheese on flour tortillas, onions and cilantro with a side of salsa verde.");
        food2.setPrice(1150);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list5.add(food2);

        food3 = new Food();
        food3.setName("Burritos");
        food3.setId(new ObjectId());
        food3.setDescription(
                "Your choice of meat or veggies wrapped in a flour tortilla filled with pinto beans, rice, cheese, onions, cilantro, salsa verde and a side of salsa roja.");
        food3.setPrice(1125);
        food3.setImg("image_" + food3.getId().toHexString() + ".jpg");
        try {
            foods.add(food3);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list5.add(food3);

        food4 = new Food();
        food4.setName("Taco");
        food4.setId(new ObjectId());
        food4.setDescription(
                "Build on freshly house made corn tortilla. Choice of meat or veggie served with cilantro, onion, salsa verde and salsa roja.");
        food4.setPrice(360);
        food4.setImg("image_" + food4.getId().toHexString() + ".jpg");
        try {
            foods.add(food4);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list5.add(food4);

        defaultRestaurant5.setName("Carmelo's Tacos");
        defaultRestaurant5.setRating(4.7);
        defaultRestaurant5.setFoods(list5);
        defaultRestaurant5.setId(new ObjectId());
        defaultRestaurant5.setImg("image_" + defaultRestaurant5.getId().toHexString() + ".jpg");
        defaultRestaurant5.setThumbnail(
                "thumbnail_" + defaultRestaurant5.getId().toHexString() + ".jpg");

        // restaurant 6
        final Restaurant defaultRestaurant6 = new Restaurant();
        // add 2 foods for restaurant 6
        List<Food> list6 = new ArrayList<>();
        food1 = new Food();
        food1.setName("Kashiba Deluxe Holiday Platter");
        food1.setId(new ObjectId());
        food1.setDescription(
                "Surprise your family this holiday season. Presented and wrapped with a decorative Japanese platter (yours to keep). Platter typically includes 2 pieces each of: Toro, Akami (lean tuna), Kampachi (Amberjack), King Salmon, Zuke (marinated lean tuna), Scallop, Uni (sea urchin), King Crab, Aburi Toro (seared), Sweet Shrimp.");
        food1.setPrice(10000);
        food1.setImg("image_" + food1.getId().toHexString() + ".jpg");
        try {
            foods.add(food1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        list6.add(food1);

        food2 = new Food();
        food2.setName("Kashiba Omakase Dinner Box");
        food2.setId(new ObjectId());
        food2.setDescription(
                "Pictured items may vary depending on Chef's assessment of fish quality on the day of pick-up. Box will include approximately 10 pieces of sushi, 9 pieces of sashimi, tamago, and Chef's black cod. Special rice is included in a separate container (not the pictured bowl). There are no substitutions or allergy accommodations available for this offering. In lieu of tipping, a 15% service charge is included in every bill. 100% of that amount is paid to employees involved in bringing you this special offering in the form of wages and benefits, including chefs, customer service and support staff. We are unable to accept gift cards for this offering.");
        food2.setPrice(15000);
        food2.setImg("image_" + food2.getId().toHexString() + ".jpg");
        try {
            foods.add(food2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        list6.add(food2);

        defaultRestaurant6.setName("Sushi Kashiba");
        defaultRestaurant6.setRating(4.7);
        defaultRestaurant6.setFoods(list6);
        defaultRestaurant6.setId(new ObjectId());
        defaultRestaurant6.setImg("image_" + defaultRestaurant6.getId().toHexString() + ".jpg");
        defaultRestaurant6.setThumbnail(
                "thumbnail_" + defaultRestaurant6.getId().toHexString() + ".jpg");

        try {
            addRestaurant(defaultRestaurant1);
            addRestaurant(defaultRestaurant2);
            addRestaurant(defaultRestaurant3);
            addRestaurant(defaultRestaurant4);
            addRestaurant(defaultRestaurant5);
            addRestaurant(defaultRestaurant6);
        } catch (Exception e) {
            log.error("MenuController > construct > adding default menus > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get restaurant by id
     *
     * @param uuid restaurant id
     * @return restaurant get by id
     */
    @Nullable
    public Restaurant getRestaurant(@Nonnull ObjectId uuid) {
        log.debug("RestaurantController > getRestaurant({})", uuid);
        return restaurants.get(uuid);
    }

    /**
     * get all restaurants
     *
     * @return all restaurants
     */
    @Nonnull
    public Collection<Restaurant> getRestaurants() {
        log.debug("MenuController > getMenus()");
        return restaurants.getAll();
    }

    /**
     * delete food from restaurant*
     *
     * @param restaurantId restaurant Id
     * @param foodId food Id to delete
     * @return restaurant with food list updated
     * @throws Exception when id is invalid
     */
    @Nonnull
    public Restaurant deleteItem(@Nonnull ObjectId restaurantId, @Nonnull ObjectId foodId)
            throws Exception {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) {
            throw new Exception("Non-exist shopping menu.");
        }

        if (!restaurant.getFoods().contains(foodId)) {
            throw new Exception("Cannot remove a non-exist food");
        }

        restaurant.getFoods().remove(foodId);

        restaurants.update(restaurant);
        return restaurant;
    }

    /**
     * Add restaurant to DB
     *
     * @param restaurant restaurant
     * @return return customer added
     * @throws Exception when restaurant is invalid or restaurant has already in the DB
     */
    @Nonnull
    public Restaurant addRestaurant(@Nonnull Restaurant restaurant) throws Exception {
        log.debug("MenuController > addMenu(...)");
        if (!restaurant.isValid()) {
            throw new Exception("InvalidMenuException");
        }

        ObjectId id = restaurant.getId();

        if (id != null && restaurants.get(id) != null) {
            throw new Exception("DuplicateKeyException");
        }

        return restaurants.add(restaurant);
    }

    /**
     * Update restaurant
     *
     * @param restaurant updated restaurant
     * @throws Exception when restaurant is invalid
     */
    public void updateRestaurant(@Nonnull Restaurant restaurant) throws Exception {
        log.debug("MenuController > updateMenu(...)");
        restaurants.update(restaurant);
    }

    /**
     * Delete restaurant
     *
     * @param id restaurant id
     * @throws Exception when id is invalid
     */
    public void deleteRestaurant(@Nonnull ObjectId id) throws Exception {
        log.debug("MenuController > deleteMenu(...)");
        restaurants.delete(id);
    }
}
