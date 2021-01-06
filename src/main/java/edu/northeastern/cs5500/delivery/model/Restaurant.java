package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Store restaurant information id: restaurant ID foods: a list of food provided by this restaurant
 * name: restaurant name rating: restaurant rating thumbnail: restaurant thumbnail, can be used on
 * the top of a certain restaurant page img: restaurant image showed in local restaurants list
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Data
public class Restaurant implements Model {
    private ObjectId id;
    private List<Food> foods;
    private String name;
    private double rating;
    private String thumbnail;
    private String img;

    /**
     * Determine whether this restaurant is valid
     *
     * @return true when the restaurant provided no food
     */
    @JsonIgnore
    public boolean isValid() {
        return foods != null;
    }
}
