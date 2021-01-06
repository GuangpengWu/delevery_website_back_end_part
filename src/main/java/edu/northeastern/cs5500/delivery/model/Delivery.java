package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Store delivery information ("delivery" means "order") id: the delivery(order) ID address: the
 * destination where the order should be sent to phone: client contact phone number customerId:
 * customer ID who placed this order restaurantName: restaurant name who will prepare food
 * restaurantId: restaurant ID instruction: specific requirement for this delivery(order)
 * orderItems: a list of items added in this order date: date placed this order
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Delivery implements Model {
    private ObjectId id;
    @Nonnull private String address;
    @Nonnull private String phone;
    @Nonnull private String instruction;
    @Nonnull private ObjectId customerId;
    @Nonnull private ObjectId restaurantId;
    @Nonnull private String restaurantName;
    @Nonnull private List<OrderItem> orderItems;
    private Date date;

    /**
     * Determine whether the delivery is valid based on customerid, restaurantId and orderItems
     *
     * @return true if this delivery is valid
     */
    @JsonIgnore
    public boolean isValid() {
        return !orderItems.isEmpty() && customerId != null && restaurantId != null;
    }
}
