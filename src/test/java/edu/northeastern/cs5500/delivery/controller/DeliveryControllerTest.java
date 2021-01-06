package edu.northeastern.cs5500.delivery.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.model.OrderItem;
import edu.northeastern.cs5500.delivery.repository.InMemoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

class DeliveryControllerTest {

    @Test
    void testCanAddDelivery() {
        DeliveryController deliveryController =
                new DeliveryController(new InMemoryRepository<Delivery>());
        assertEquals(deliveryController.getDeliverys().size(), 0);
        OrderItem orderItem = new OrderItem(new ObjectId(), "name", 100, 1);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Delivery delivery1 = new Delivery();
        delivery1.setCustomerId(new ObjectId());
        delivery1.setRestaurantId(new ObjectId());
        delivery1.setOrderItems(items);
        Delivery delivery2 = new Delivery();
        delivery2.setCustomerId(new ObjectId());
        delivery2.setRestaurantId(new ObjectId());
        delivery2.setOrderItems(items);
        try {
            deliveryController.addDelivery(delivery1);
            deliveryController.addDelivery(delivery2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(deliveryController.getDeliverys().size(), 2);
    }

    @Test
    void testCanReplaceDelivery() {
        DeliveryController deliveryController =
                new DeliveryController(new InMemoryRepository<Delivery>());
        assertEquals(deliveryController.getDeliverys().size(), 0);
        OrderItem orderItem = new OrderItem(new ObjectId(), "name", 100, 1);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Delivery delivery2 = new Delivery();
        delivery2.setCustomerId(new ObjectId());
        delivery2.setRestaurantId(new ObjectId());
        delivery2.setOrderItems(items);
        try {
            deliveryController.addDelivery(delivery2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(deliveryController.getDeliverys().size(), 1);
        delivery2 = deliveryController.getDelivery(delivery2.getId());
        assertEquals(null, delivery2.getAddress());
        delivery2.setAddress("new address");
        try {
            deliveryController.updateDelivery(delivery2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        delivery2 = deliveryController.getDelivery(delivery2.getId());
        assertEquals("new address", delivery2.getAddress());
    }

    @Test
    void testCanDeleteDelivery() {
        DeliveryController deliveryController =
                new DeliveryController(new InMemoryRepository<Delivery>());
        assertEquals(deliveryController.getDeliverys().size(), 0);
        OrderItem orderItem = new OrderItem(new ObjectId(), "name", 100, 1);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Delivery delivery1 = new Delivery();
        delivery1.setCustomerId(new ObjectId());
        delivery1.setRestaurantId(new ObjectId());
        delivery1.setOrderItems(items);
        Delivery delivery2 = new Delivery();
        delivery2.setCustomerId(new ObjectId());
        delivery2.setRestaurantId(new ObjectId());
        delivery2.setOrderItems(items);
        try {
            deliveryController.addDelivery(delivery1);
            deliveryController.addDelivery(delivery2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(deliveryController.getDeliverys().size(), 2);

        try {
            deliveryController.deleteDelivery(delivery1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(deliveryController.getDeliverys().size(), 1);
    }
}
