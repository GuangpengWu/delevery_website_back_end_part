package edu.northeastern.cs5500.delivery.view;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.delivery.JsonTransformer;
import edu.northeastern.cs5500.delivery.controller.CustomerController;
import edu.northeastern.cs5500.delivery.controller.ShoppingCartController;
import edu.northeastern.cs5500.delivery.model.Customer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class CustomerView implements View {

    @Inject
    CustomerView() {}

    @Inject JsonTransformer jsonTransformer;
    @Inject CustomerController customerController;
    @Inject ShoppingCartController shoppingCartController;

    @Override
    public void register() {
        log.info("CustomerView > register");

        get(
                "/customer",
                (request, response) -> {
                    log.debug("/customer");
                    response.type("application/json");
                    return customerController.getCustomers();
                },
                jsonTransformer);

        get(
                "/customer/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    log.debug("/customer/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    Customer customer = customerController.getCustomer(id);
                    if (customer == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    return customer;
                },
                jsonTransformer);

        get(
                "/customerbyname/:name",
                (request, response) -> {
                    final String paramId = request.params(":name");
                    log.debug("/customerbyname/:name<{}>", paramId);
                    final String name = new String(paramId);
                    Customer customer = customerController.getCustomerByName(name);
                    if (customer == null) {
                        response.status(400); // bad request
                        return "";
                    } else {
                        response.type("application/json");
                        return customer;
                    }
                },
                jsonTransformer);

        post(
                "/customer/createCustomer",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Customer customer = mapper.readValue(request.body(), Customer.class);
                    if (!customer.isValid()) {
                        System.out.println("cusotmer is invalid!");
                        response.status(400);
                        return "";
                    }
                    ;
                    customer.setId(null);
                    try {
                        customer = customerController.addCustomer(customer);
                        customer.setShoppingCartId(shoppingCartController.createNewShoppingCart());
                        customerController.updateCustomer(customer);
                    } catch (IllegalArgumentException e) {
                        response.status(400); // bad request
                        return "";
                    }

                    return customer;
                },
                jsonTransformer);

        put(
                "/customer/updateCustomer",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Customer customer = mapper.readValue(request.body(), Customer.class);
                    if (!customer.isValid()) {
                        response.status(400);
                        return "";
                    }

                    customerController.updateCustomer(customer);
                    return customer;
                });

        put(
                "/customer/deleteCustomer",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Customer customer = mapper.readValue(request.body(), Customer.class);
                    if (!customer.isValid()) {
                        response.status(400);
                        return "";
                    }

                    customerController.updateCustomer(customer);
                    return customer;
                });

        delete(
                "/customer",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Customer customer = mapper.readValue(request.body(), Customer.class);

                    customerController.deleteCustomer(customer.getId());
                    return customer;
                });
    }
}
