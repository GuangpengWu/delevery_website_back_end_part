package edu.northeastern.cs5500.delivery.repository;

import dagger.Module;
import dagger.Provides;
import edu.northeastern.cs5500.delivery.model.Customer;
import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.Restaurant;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import edu.northeastern.cs5500.delivery.service.MongoDBService;

/** Repository Module */
@Module
public class RepositoryModule {

    @Provides
    public GenericRepository<Delivery> provideDeliveryRepository(MongoDBService mongoDBService) {
        return new MongoDBRepository<>(Delivery.class, mongoDBService);
    }

    @Provides
    public GenericRepository<Customer> provideCustomerRepository(MongoDBService mongoDBService) {
        return new CustomerMongoDBRepository<>(Customer.class, mongoDBService);
    }

    @Provides
    public GenericRepository<Restaurant> provideRestaurantRepository(
            MongoDBService mongoDBService) {
        return new MongoDBRepository<>(Restaurant.class, mongoDBService);
    }

    @Provides
    public GenericRepository<Food> provideFoodRepository(MongoDBService mongoDBService) {
        return new MongoDBRepository<>(Food.class, mongoDBService);
    }

    @Provides
    public GenericRepository<ShoppingCart> provideShoppingCartRepository(
            MongoDBService mongoDBService) {
        return new MongoDBRepository<>(ShoppingCart.class, mongoDBService);
    }
}
