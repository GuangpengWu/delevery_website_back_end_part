package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.Food;
import edu.northeastern.cs5500.delivery.model.ShoppingCart;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Shoppingcart Controller
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class ShoppingCartController {
    private final GenericRepository<ShoppingCart> shoppingCarts;
    private final GenericRepository<Food> foods;

    /**
     * constructor
     *
     * @param shoppingCartRepository shoppingCart Repository
     * @param foodRepository food Repository
     */
    @Inject
    ShoppingCartController(
            GenericRepository<ShoppingCart> shoppingCartRepository,
            GenericRepository<Food> foodRepository) {
        shoppingCarts = shoppingCartRepository;
        foods = foodRepository;

        log.info("ShoppingCartController > construct");

        if (shoppingCarts.count() > 0) {
            return;
        }

        log.info("ShoppingCartController > construct > adding default shoppingCarts");

        try {
            ShoppingCart shoppingCart1 = new ShoppingCart();
            Map<String, Integer> items1 = new HashMap<>();
            for (Food food : foods.getAll()) {
                items1.put(food.getId().toString(), 1);
            }
            shoppingCart1.setItems(items1);
            shoppingCart1.setTotalPrice(15L);

            ShoppingCart shoppingCart2 = new ShoppingCart();
            Map<String, Integer> items2 = new HashMap<>();
            for (Food food : foods.getAll()) {
                items2.put(food.getId().toString(), 2);
            }
            shoppingCart2.setItems(items2);
            shoppingCart2.setTotalPrice(30L);

            this.addShoppingCart(shoppingCart1);
            this.addShoppingCart(shoppingCart2);
        } catch (Exception e) {
            log.error(
                    "ShoppingCartController > construct > adding default ShoppingCarts > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get shopping cart by id
     *
     * @param uuid shopping cart id
     * @return shopping cart
     */
    @Nullable
    public ShoppingCart getShoppingCart(@Nonnull ObjectId uuid) {
        log.debug("ShoppingCartController > getShoppingCart({})", uuid);
        return shoppingCarts.get(uuid);
    }

    /**
     * Get all shopping carts
     *
     * @return all shopping carts
     */
    @Nonnull
    public Collection<ShoppingCart> getShoppingCarts() {
        log.debug("ShoppingCartController > getShoppingCarts()");
        return shoppingCarts.getAll();
    }

    /**
     * Create a new shopping cart*
     *
     * @return new shopping cart id
     * @throws Exception when shopping cart is invalid
     */
    @Nonnull
    public ObjectId createNewShoppingCart() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new HashMap<>());
        cart.setTotalPrice(0L);
        return this.addShoppingCart(cart).getId();
    }

    /**
     * Add a food to shopping cart
     *
     * @param shoppingCartId shopping Cart Id
     * @param foodId id of food to add
     * @return updated shopping cart with new food in it
     * @throws Exception when shoppingCartId or foodId is invalid
     */
    @Nonnull
    public ShoppingCart addItem(@Nonnull ObjectId shoppingCartId, @Nonnull ObjectId foodId)
            throws Exception {
        if (foods.get(foodId) == null) {
            throw new Exception("Invalid food id");
        }
        long foodPrice = foods.get(foodId).getPrice();
        ShoppingCart shoppingCart = shoppingCarts.get(shoppingCartId);
        if (shoppingCart == null) {
            throw new Exception("Non-exist shopping cart. ");
        }
        shoppingCart
                .getItems()
                .put(
                        foodId.toString(),
                        shoppingCart.getItems().getOrDefault(foodId.toString(), 0) + 1);
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + foodPrice);
        shoppingCarts.update(shoppingCart);
        return shoppingCart;
    }

    /**
     * Delete a food from shopping cart*
     *
     * @param shoppingCartId shopping Cart Id
     * @param foodId Id of food to delete
     * @return updated shopping cart with food deleted
     * @throws Exception when shoppingCartId or foodId is invalid
     */
    @Nonnull
    public ShoppingCart deleteItem(@Nonnull ObjectId shoppingCartId, @Nonnull ObjectId foodId)
            throws Exception {
        ShoppingCart cart = shoppingCarts.get(shoppingCartId);
        if (cart == null) {
            throw new Exception("Non-exist shopping cart.");
        }

        String id = foodId.toString();
        if (!cart.getItems().containsKey(id)) {
            throw new Exception("Cannot remove a non-exist food");
        }

        int currNum = cart.getItems().get(id);
        if (currNum <= 1) {
            cart.getItems().remove(id);
        } else {
            cart.getItems().put(id, currNum - 1);
        }
        cart.setTotalPrice(cart.getTotalPrice() - foods.get(foodId).getPrice());
        shoppingCarts.update(cart);
        return cart;
    }

    /**
     * Calculate total price of shoppingcart
     *
     * @param shoppingCartIdshoppingCartId
     * @returns totalPrice of foods in the shopping car the total price is record as long, for
     *     example, $28.15 is represented as 2815
     */
    @Nonnull
    public long calculateTotalPrice(@Nonnull ObjectId shoppingCartId) throws Exception {
        ShoppingCart cart = shoppingCarts.get(shoppingCartId);
        if (cart == null) {
            throw new Exception("Non-exist shopping cart.");
        }

        long totalPrice = 0L;
        for (Map.Entry<String, Integer> items : cart.getItems().entrySet()) {
            totalPrice += foods.get(new ObjectId(items.getKey())).getPrice();
        }
        cart.setTotalPrice(totalPrice);
        return totalPrice;
    }

    /**
     * Add a shopping cart
     *
     * @param shoppingCart shopping Cart
     * @return shopping Cart added
     * @throws Exception when shopping cart is invalid
     */
    @Nonnull
    public ShoppingCart addShoppingCart(@Nonnull ShoppingCart shoppingCart) throws Exception {
        log.debug("ShoppingCartController > addShoppingCart(...)");
        if (!shoppingCart.isValid()) {
            throw new Exception("InvalidShoppingCartException");
        }

        ObjectId id = shoppingCart.getId();

        if (id != null && shoppingCarts.get(id) != null) {
            throw new Exception("DuplicateKeyException");
        }

        return shoppingCarts.add(shoppingCart);
    }

    /**
     * Update Shopping Cart
     *
     * @param shoppingCart shopping cart with updated information
     * @throws Exception when shopping cart is invalid
     */
    public void updateShoppingCart(@Nonnull ShoppingCart shoppingCart) throws Exception {
        log.debug("ShoppingCartController > updateShoppingCart(...)");
        shoppingCarts.update(shoppingCart);
    }

    /**
     * Delete Shopping Cart
     *
     * @param id shopping cart id
     * @throws Exception when shopping cart id is invalid
     */
    public void deleteShoppingCart(@Nonnull ObjectId id) throws Exception {
        log.debug("ShoppingCartController > deleteshoppingCart(...)");
        shoppingCarts.delete(id);
    }

    /**
     * Empty this shopping cart
     *
     * @param id shopping cart id
     * @throws Exception when shopping cart id is invalid
     */
    public void empty(@Nonnull ObjectId id) throws Exception {
        ShoppingCart cart = shoppingCarts.get(id);
        cart.setItems(new HashMap<>());
        cart.setTotalPrice(0L);
        shoppingCarts.update(cart);
    }
}
