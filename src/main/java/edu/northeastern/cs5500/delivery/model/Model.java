package edu.northeastern.cs5500.delivery.model;

import org.bson.types.ObjectId;

/**
 * Define getter and setter, this interface is used by all the classes in model folder
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
public interface Model {
    /** get id */
    ObjectId getId();

    /** set id */
    void setId(ObjectId id);
}
