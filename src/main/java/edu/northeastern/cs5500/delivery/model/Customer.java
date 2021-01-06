package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Store customer information id: customer ID in database, which is also the primary key in DB
 * customerName: username, which customer can login by password: password of the customer account
 * email: customer email address phone: customer phone address: customer's address deliveries: a
 * list of deliveries(orders) the customer being placed and has placed shoppingCartId: the shopping
 * cart ID related to this customer
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer implements Model {
    private ObjectId id;
    @Nonnull private String customerName;
    @Nonnull private String password;
    @Nonnull private String email;
    @Nonnull private String phone;
    @Nonnull private String address;
    private List<ObjectId> deliveries;
    private ObjectId shoppingCartId;

    /**
     * Determine whether the customer object is valid based on customerName
     *
     * @return true if this customer is valid
     */
    @JsonIgnore
    public boolean isValid() {
        return customerName != null && !customerName.isEmpty();
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }
}
