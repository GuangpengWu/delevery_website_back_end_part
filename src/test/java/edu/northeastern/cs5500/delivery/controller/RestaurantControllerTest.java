package edu.northeastern.cs5500.delivery.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.Restaurant;
import edu.northeastern.cs5500.delivery.repository.InMemoryRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class RestaurantControllerTest {

    @Test
    void testCanAddRestaurant() {
        RestaurantController restaurantController =
                new RestaurantController(
                        new InMemoryRepository<Restaurant>(), new InMemoryRepository<Food>());

        int size = restaurantController.getRestaurants().size();
        assertEquals(size, restaurantController.getRestaurants().size());

        Restaurant restaurant = new Restaurant();
        restaurant.setFoods(new ArrayList<>());

        try {
            restaurant = restaurantController.addRestaurant(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(size + 1, restaurantController.getRestaurants().size());
    }

    @Test
    void testCanGetAllRestaurants() {
        RestaurantController restaurantController =
                new RestaurantController(
                        new InMemoryRepository<Restaurant>(), new InMemoryRepository<Food>());
        assertEquals(6, restaurantController.getRestaurants().size());
    }

    @Test
    void testCanUpdateRestaurant() {
        RestaurantController restaurantController =
                new RestaurantController(
                        new InMemoryRepository<Restaurant>(), new InMemoryRepository<Food>());
        Restaurant restaurant = new Restaurant();
        restaurant.setFoods(new ArrayList<>());

        try {
            restaurant = restaurantController.addRestaurant(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Food food = new Food();

        ArrayList<Food> array = new ArrayList<>();
        array.add(food);
        restaurant.setFoods(array);

        try {
            restaurantController.updateRestaurant(restaurant);
            restaurant = restaurantController.getRestaurant(restaurant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(7, restaurantController.getRestaurants().size());
    }

    @Test
    void testCanDeleteRestaurant() {
        // Create a Menu controller.
        RestaurantController restaurantController =
                new RestaurantController(
                        new InMemoryRepository<Restaurant>(), new InMemoryRepository<Food>());

        int size = restaurantController.getRestaurants().size();
        assertEquals(size, restaurantController.getRestaurants().size());
        // Add a Menu.
        Restaurant restaurant = new Restaurant();
        restaurant.setFoods(new ArrayList<>());

        try {
            restaurant = restaurantController.addRestaurant(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(size + 1, restaurantController.getRestaurants().size());

        // Delete Menu.
        try {
            restaurantController.deleteRestaurant(restaurant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(null, restaurantController.getRestaurant(restaurant.getId()));
    }
}
