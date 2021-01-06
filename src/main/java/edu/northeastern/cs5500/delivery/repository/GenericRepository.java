package edu.northeastern.cs5500.delivery.repository;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.bson.types.ObjectId;

/** GenericRepository interface */
public interface GenericRepository<T> {

    public T get(@Nonnull ObjectId id);

    /**
     * get object by fieldname and value of this field eg. for customer class, given fieldname as
     * "name", value as "John", it will get cusotmer whose name is "John"
     */
    public T get(@Nonnull String fieldname, String value);

    public T add(@Nonnull T item) throws Exception;

    public T update(@Nonnull T item);

    public void delete(@Nonnull ObjectId id);

    public Collection<T> getAll();

    public long count();
}
