package edu.northeastern.cs5500.delivery.model;

import dagger.Module;
import dagger.Provides;

/** @author: Taowen Liu, Qianlin Ma, Guangpeng Wu */
@Module
public class ModelModule {

    @Provides
    public Class<Delivery> provideDeliveryClass() {
        return Delivery.class;
    }

    @Provides
    public Class<Customer> provideCustomerClass() {
        return Customer.class;
    }

    @Provides
    public Class<Restaurant> provideRestaurantClass() {
        return Restaurant.class;
    }

    @Provides
    public Class<Food> provideFoodClass() {
        return Food.class;
    }

    @Provides
    public Class<ShoppingCart> provideShoppingCartClass() {
        return ShoppingCart.class;
    }

    @Provides
    public Class<OrderItem> provideOrderItemClass() {
        return OrderItem.class;
    }
}
