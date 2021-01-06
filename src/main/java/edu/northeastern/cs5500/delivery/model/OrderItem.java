package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Store order item information foodId: foodName: price: count:
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
@AllArgsConstructor
public class OrderItem {
    private ObjectId foodId;
    private String foodName;
    private long price;
    private Integer count;

    /**
     * Determine whether this orderItem is valid
     *
     * @return true when foodID is not null
     */
    @JsonIgnore
    public boolean isValid() {
        return foodId != null;
    }
}
