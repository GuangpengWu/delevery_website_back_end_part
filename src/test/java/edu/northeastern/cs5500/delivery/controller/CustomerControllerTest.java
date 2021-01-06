package edu.northeastern.cs5500.delivery.controller;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertEquals;

import edu.northeastern.cs5500.delivery.model.Customer;
import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import edu.northeastern.cs5500.delivery.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

class CustomerControllerTest {

    @Test
    void testRegisterCreatesValidCustomers() {

        CustomerController customerController1 =
                new CustomerController(
                        new InMemoryRepository<Customer>(),
                        new InMemoryRepository<ShoppingCart>(),
                        new InMemoryRepository<Food>(),
                        new InMemoryRepository<Delivery>());

        for (Customer customer : customerController1.getCustomers()) {
            assertWithMessage(customer.getId().toHexString()).that(customer.isValid()).isTrue();
        }
    }

    @Test
    void testCanGetAllCustomers() throws Exception {
        CustomerController customerController1 =
                new CustomerController(
                        new InMemoryRepository<Customer>(),
                        new InMemoryRepository<ShoppingCart>(),
                        new InMemoryRepository<Food>(),
                        new InMemoryRepository<Delivery>());
        assertEquals(customerController1.getCustomers().size(), 2);
        Customer customer1 =
                new Customer(
                        "Jack",
                        "abcde",
                        "jack@gmail.com",
                        "12345",
                        "123th 456St, seattle, WA, 98123");
        Customer customer2 =
                new Customer(
                        "mary",
                        "hhhhh",
                        "mary@gmail.com",
                        "00000",
                        "110th 43st, seattle, WA, 98123");
        try {
            customerController1.addCustomer(customer1);
            customerController1.addCustomer(customer2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(customerController1.getCustomers().size(), 4);
    }

    @Test
    void testCanAddCustomer() throws Exception {
        CustomerController customerController1 =
                new CustomerController(
                        new InMemoryRepository<Customer>(),
                        new InMemoryRepository<ShoppingCart>(),
                        new InMemoryRepository<Food>(),
                        new InMemoryRepository<Delivery>());
        assertEquals(customerController1.getCustomers().size(), 2);
        Customer customer1 =
                new Customer(
                        "Jack",
                        "abcde",
                        "jack@gmail.com",
                        "12345",
                        "123th 456St, seattle, WA, 98123");
        try {
            customerController1.addCustomer(customer1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(customerController1.getCustomers().size(), 3);
    }

    @Test
    void testCanUpdateCustomer() throws Exception {
        CustomerController customerController1 =
                new CustomerController(
                        new InMemoryRepository<Customer>(),
                        new InMemoryRepository<ShoppingCart>(),
                        new InMemoryRepository<Food>(),
                        new InMemoryRepository<Delivery>());
        Customer customer1 =
                new Customer(
                        "Jack",
                        "abcde",
                        "jack@gmail.com",
                        "12345",
                        "123th 456St, seattle, WA, 98123");
        try {
            customerController1.addCustomer(customer1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        customer1.setCustomerName("james");
        try {
            customerController1.updateCustomer(customer1);
        } catch (Exception e1) {
            e1.printStackTrace();
            throw e1;
        }
        customer1 = customerController1.getCustomer(customer1.getId());
        assertEquals(customer1.getCustomerName(), "james");
    }

    @Test
    void testCanDeleteCustomer() {
        CustomerController customerController1 =
                new CustomerController(
                        new InMemoryRepository<Customer>(),
                        new InMemoryRepository<ShoppingCart>(),
                        new InMemoryRepository<Food>(),
                        new InMemoryRepository<Delivery>());
        assertEquals(customerController1.getCustomers().size(), 2);
        Customer customer1 =
                new Customer(
                        "Jack",
                        "abcde",
                        "jack@gmail.com",
                        "12345",
                        "123th 456St, seattle,ma WA, 98123");
        try {
            customerController1.addCustomer(customer1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(customerController1.getCustomers().size(), 3);

        try {
            customerController1.deleteCustomer(customer1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(customerController1.getCustomers().size(), 2);
    }
}
