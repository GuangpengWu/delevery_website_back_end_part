package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.Delivery;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.Calendar;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Delivery Controller
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class DeliveryController {
    private final GenericRepository<Delivery> deliverys;

    /**
     * constructor
     *
     * @param deliveryRepository delivery Repository
     */
    @Inject
    DeliveryController(GenericRepository<Delivery> deliveryRepository) {
        deliverys = deliveryRepository;

        log.info("DeliveryController > construct");
    }

    /**
     * Get delivery by id
     *
     * @param uuid delivery id
     * @return delivery get by id
     */
    @Nullable
    public Delivery getDelivery(@Nonnull ObjectId uuid) {
        log.debug("DeliveryController > getDelivery({})", uuid);
        return deliverys.get(uuid);
    }

    /**
     * Get all deliveries
     *
     * @return all deliveries
     */
    @Nonnull
    public Collection<Delivery> getDeliverys() {
        log.debug("DeliveryController > getDeliverys()");
        return deliverys.getAll();
    }

    /**
     * Add delivery to DB
     *
     * @return delivery
     * @throws Exception when delivery is invalid
     */
    @Nonnull
    public Delivery addDelivery(@Nonnull Delivery delivery) throws Exception {
        log.debug("DeliveryController > addDelivery(...)");
        if (!delivery.isValid()) {
            throw new Exception("InvalidDeliveryException");
        }

        ObjectId id = delivery.getId();

        if (id != null && deliverys.get(id) != null) {
            throw new Exception("DuplicateKeyException");
        }
        delivery.setDate(Calendar.getInstance().getTime());

        return deliverys.add(delivery);
    }

    /**
     * Update delivery
     *
     * @param delivery new delivery with updated informationd
     * @throws Exception when delivery is invalid
     */
    public void updateDelivery(@Nonnull Delivery delivery) throws Exception {
        log.debug("DeliveryController > updateDelivery(...)");
        deliverys.update(delivery);
    }

    /**
     * Delete delivery by id
     *
     * @param id delivery id
     * @throws Exception when id is invalid
     */
    public void deleteDelivery(@Nonnull ObjectId id) throws Exception {
        log.debug("DeliveryController > deleteDelivery(...)");
        deliverys.delete(id);
    }
}
