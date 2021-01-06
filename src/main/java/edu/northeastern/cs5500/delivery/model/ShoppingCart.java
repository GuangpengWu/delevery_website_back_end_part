package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Store shopping cart information id: shopping cart id totalPrice: total price of items in shopping
 * cart, stored the data as long items: items in the shopping cart, as Map<Food ObjectId, Quantity>
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
public class ShoppingCart implements Model {
    private ObjectId id;
    private long totalPrice;
    private Map<String, Integer> items; // Map<Food ObjectId, Quantity>.

    /**
     * Determine whether this shopping is valid
     *
     * @return true when shopping cart is not empty
     */
    @JsonIgnore
    public boolean isValid() {
        return items != null;
    }
}
