package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Store food information id: food ID name: food name description: food description price: food
 * price img: food image
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
public class Food implements Model {
    private ObjectId id;
    private String name;
    private String description;
    private long price;
    private String img;

    /**
     * Determine whether this food is valid based on foodname
     *
     * @return true when food is not empty
     */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
