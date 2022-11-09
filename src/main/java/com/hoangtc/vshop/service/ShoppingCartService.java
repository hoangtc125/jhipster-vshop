package com.hoangtc.vshop.service;

import com.hoangtc.vshop.domain.ShoppingCart;
import com.hoangtc.vshop.repository.ShoppingCartRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ShoppingCart}.
 */
@Service
public class ShoppingCartService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCart the entity to save.
     * @return the persisted entity.
     */
    public ShoppingCart save(ShoppingCart shoppingCart) {
        log.debug("Request to save ShoppingCart : {}", shoppingCart);
        return shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Update a shoppingCart.
     *
     * @param shoppingCart the entity to save.
     * @return the persisted entity.
     */
    public ShoppingCart update(ShoppingCart shoppingCart) {
        log.debug("Request to update ShoppingCart : {}", shoppingCart);
        return shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Partially update a shoppingCart.
     *
     * @param shoppingCart the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShoppingCart> partialUpdate(ShoppingCart shoppingCart) {
        log.debug("Request to partially update ShoppingCart : {}", shoppingCart);

        return shoppingCartRepository
            .findById(shoppingCart.getId())
            .map(existingShoppingCart -> {
                if (shoppingCart.getPlacedDate() != null) {
                    existingShoppingCart.setPlacedDate(shoppingCart.getPlacedDate());
                }
                if (shoppingCart.getStatus() != null) {
                    existingShoppingCart.setStatus(shoppingCart.getStatus());
                }
                if (shoppingCart.getTotalPrice() != null) {
                    existingShoppingCart.setTotalPrice(shoppingCart.getTotalPrice());
                }
                if (shoppingCart.getPaymentMethod() != null) {
                    existingShoppingCart.setPaymentMethod(shoppingCart.getPaymentMethod());
                }
                if (shoppingCart.getPaymentReference() != null) {
                    existingShoppingCart.setPaymentReference(shoppingCart.getPaymentReference());
                }

                return existingShoppingCart;
            })
            .map(shoppingCartRepository::save);
    }

    /**
     * Get all the shoppingCarts.
     *
     * @return the list of entities.
     */
    public List<ShoppingCart> findAll() {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartRepository.findAll();
    }

    /**
     * Get one shoppingCart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ShoppingCart> findOne(String id) {
        log.debug("Request to get ShoppingCart : {}", id);
        return shoppingCartRepository.findById(id);
    }

    /**
     * Delete the shoppingCart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ShoppingCart : {}", id);
        shoppingCartRepository.deleteById(id);
    }
}
