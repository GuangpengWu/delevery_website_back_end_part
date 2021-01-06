package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.Customer;
import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Customer Controller
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class CustomerController {
    private final GenericRepository<Delivery> deliverys;
    private final GenericRepository<ShoppingCart> shoppingCarts;
    private final GenericRepository<Food> foods;
    private final GenericRepository<Customer> customers;

    /**
     * constructor
     *
     * @param customerRepository customer Repository
     * @param shoppingCartRepository shoppingCart Repository
     * @param foodsRepository foods Repository
     * @param deliveryRepository delivery Repository
     */
    @Inject
    CustomerController(
            GenericRepository<Customer> customerRepository,
            GenericRepository<ShoppingCart> shoppingCartRepository,
            GenericRepository<Food> foodsRepository,
            GenericRepository<Delivery> deliveryRepository) {
        customers = customerRepository;
        deliverys = deliveryRepository;
        shoppingCarts = shoppingCartRepository;
        foods = foodsRepository;

        log.info("CustomerController > construct");

        if (customers.count() > 0) {
            return;
        }
        log.info("CustomerController > construct > adding default customers");

        // build frist default customer
        try {
            Customer defaultCustomer1 =
                    new Customer(
                            "Mia",
                            "abc123",
                            "mia2020@gmail.com",
                            "1234567890",
                            "1029NE 156Ave Apt1101");
            List<ObjectId> lst1 = new ArrayList<>();
            List<Delivery> allDeliveries = deliverys.getAll().stream().collect(Collectors.toList());

            for (int i = 0; i < Math.min(2, allDeliveries.size()); ++i) {
                lst1.add(allDeliveries.get(i).getId());
            }
            defaultCustomer1.setDeliveries(lst1);

            List<ShoppingCart> allShoppingCarts =
                    shoppingCarts.getAll().stream().collect(Collectors.toList());
            if (allShoppingCarts.size() > 0) {
                defaultCustomer1.setShoppingCartId(allShoppingCarts.get(0).getId());
            }
            // build second default customer
            Customer defaultCustomer2 =
                    new Customer(
                            "Jon",
                            "def456",
                            "Jon2019@gmail.com",
                            "09877654321",
                            "1101NW 119Ave Apt203");

            // set deiliveries arrtibute
            List<ObjectId> lst2 = new ArrayList<>();
            for (int i = 0; i < Math.min(2, allDeliveries.size()); ++i) {
                lst2.add(allDeliveries.get(i).getId());
            }
            defaultCustomer2.setDeliveries(lst2);
            // set shoppingcartId arrtibute;
            if (allShoppingCarts.size() > 0) {
                defaultCustomer2.setShoppingCartId(allShoppingCarts.get(0).getId());
            }
            addCustomer(defaultCustomer1);
            addCustomer(defaultCustomer2);
        } catch (Exception e) {
            log.error("CustomerController > construct > adding default customers > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get customer by id
     *
     * @param uuid customer id
     * @return customer get by id
     */
    @Nullable
    public Customer getCustomer(@Nonnull ObjectId uuid) {
        log.debug("CustomerController > getCustomer({})", uuid);
        return customers.get(uuid);
    }

    /**
     * Get customer by name
     *
     * @param name customer name
     * @return customer get by name
     */
    @Nullable
    public Customer getCustomerByName(@Nonnull String name) {
        log.debug("CustomerController > getCustomerByName({})", name);
        return customers.get("customerName", name);
    }

    /** Get all customers from DB */
    @Nonnull
    public Collection<Customer> getCustomers() {
        log.debug("CustomerController > getCustomers()");
        return customers.getAll();
    }

    /**
     * Add customer to DB
     *
     * @param customer customer
     * @return return customer added
     * @throws Exception when customer is invalid or customer name has already in the DB, throw
     *     exception
     */
    @Nonnull
    public Customer addCustomer(@Nonnull Customer customer) throws Exception {
        log.debug("CustomerController > addCustomer(...)");
        if (!customer.isValid()) {
            throw new Exception("InvalidCustomerException");
        }

        ObjectId id = customer.getId();

        if (id != null && customers.get(id) != null) {
            throw new Exception("DuplicateKeyException");
        }
        customer.setDeliveries(new ArrayList<>());
        // May have IllegalArgumentException - duplicate name
        return customers.add(customer);
    }

    /**
     * Update customer from DB
     *
     * @param customer new customer with updated informationd
     */
    public void updateCustomer(@Nonnull Customer customer) throws Exception {
        log.debug("CustomerController > updateCustomer(...)");
        customers.update(customer);
    }

    /**
     * Delete customer from DB by id
     *
     * @param id customer id
     */
    public void deleteCustomer(@Nonnull ObjectId id) throws Exception {
        log.debug("CustomerController > deleteCustomer(...)");
        customers.delete(id);
    }
}
