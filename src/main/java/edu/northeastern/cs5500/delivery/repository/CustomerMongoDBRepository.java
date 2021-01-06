package edu.northeastern.cs5500.delivery.repository;

import static com.mongodb.client.model.Filters.eq;

import edu.northeastern.cs5500.delivery.model.Customer;
import edu.northeastern.cs5500.delivery.model.Model;
import edu.northeastern.cs5500.delivery.service.MongoDBService;
import javax.inject.Inject;
import org.bson.types.ObjectId;

/** CustomerMongoDB Repository, which extends MongoDBRepository */
public class CustomerMongoDBRepository<T extends Model> extends MongoDBRepository<T> {

    @Inject
    public CustomerMongoDBRepository(Class<T> clazz, MongoDBService mongoDBService) {
        super(clazz, mongoDBService);
    }

    /**
     * Add customer to DB When adding new users into the DB, this method can be used to validate
     * whether a input sign up username has existed in the system
     *
     * @exception IllegalArgumentException to throw "DuplicateCustomerName" when DB has same
     *     customer name as the input customer's name
     */
    @Override
    public T add(T item) throws IllegalArgumentException {
        //
        if (item.getId() == null) {
            item.setId(new ObjectId());
        }
        // cast input item to Customer
        Customer customer = (Customer) item;
        // existing is to count how many documents have the the same customerName as input customer
        long existing = collection.countDocuments(eq("customerName", customer.getCustomerName()));
        // existing customer > 0 means the system already has the same customer name, throw out
        // Exception
        if (existing > 0) {
            throw new IllegalArgumentException("DuplicateCustomerName!");
        } else {
            // if not a duplicate customer name, add it to DB
            collection.insertOne(item);
            return item;
        }
    }
}
