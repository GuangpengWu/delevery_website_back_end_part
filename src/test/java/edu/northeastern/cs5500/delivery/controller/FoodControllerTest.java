package edu.northeastern.cs5500.delivery.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

class FoodControllerTest {

    @Test
    void testCanAddFood() {
        FoodController foodController = new FoodController(new InMemoryRepository<Food>());

        int size = foodController.getFoods().size();
        assertEquals(size, foodController.getFoods().size());

        Food food = new Food();
        food.setName("food1");
        food.setPrice(10L);
        try {
            food = foodController.addFood(food);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(size + 1, foodController.getFoods().size());
    }

    @Test
    void testCanGetAllFoods() {
        FoodController foodController = new FoodController(new InMemoryRepository<Food>());
        assertEquals(2, foodController.getFoods().size());
    }

    @Test
    void testCanUpdateFood() {
        FoodController foodController = new FoodController(new InMemoryRepository<Food>());
        Food food = new Food();
        food.setName("food1");
        food.setPrice(0L);
        try {
            food = foodController.addFood(food);
        } catch (Exception e) {
            e.printStackTrace();
        }
        food.setPrice(10L);

        try {
            foodController.updateFood(food);
            food = foodController.getFood(food.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(10L, food.getPrice());
    }

    @Test
    void testCanDeleteFood() {
        FoodController foodController = new FoodController(new InMemoryRepository<Food>());

        int size = foodController.getFoods().size();
        assertEquals(size, foodController.getFoods().size());

        Food food = new Food();
        food.setName("food1");
        food.setPrice(10L);
        try {
            food = foodController.addFood(food);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(size + 1, foodController.getFoods().size());

        try {
            foodController.deleteFood(food.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(null, foodController.getFood(food.getId()));
    }
}
