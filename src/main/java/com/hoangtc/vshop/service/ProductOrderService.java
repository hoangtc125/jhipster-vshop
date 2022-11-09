package com.hoangtc.vshop.service;

import com.hoangtc.vshop.domain.ProductOrder;
import com.hoangtc.vshop.repository.ProductOrderRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ProductOrder}.
 */
@Service
public class ProductOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    /**
     * Save a productOrder.
     *
     * @param productOrder the entity to save.
     * @return the persisted entity.
     */
    public ProductOrder save(ProductOrder productOrder) {
        log.debug("Request to save ProductOrder : {}", productOrder);
        return productOrderRepository.save(productOrder);
    }

    /**
     * Update a productOrder.
     *
     * @param productOrder the entity to save.
     * @return the persisted entity.
     */
    public ProductOrder update(ProductOrder productOrder) {
        log.debug("Request to update ProductOrder : {}", productOrder);
        return productOrderRepository.save(productOrder);
    }

    /**
     * Partially update a productOrder.
     *
     * @param productOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductOrder> partialUpdate(ProductOrder productOrder) {
        log.debug("Request to partially update ProductOrder : {}", productOrder);

        return productOrderRepository
            .findById(productOrder.getId())
            .map(existingProductOrder -> {
                if (productOrder.getQuantity() != null) {
                    existingProductOrder.setQuantity(productOrder.getQuantity());
                }
                if (productOrder.getTotalPrice() != null) {
                    existingProductOrder.setTotalPrice(productOrder.getTotalPrice());
                }

                return existingProductOrder;
            })
            .map(productOrderRepository::save);
    }

    /**
     * Get all the productOrders.
     *
     * @return the list of entities.
     */
    public List<ProductOrder> findAll() {
        log.debug("Request to get all ProductOrders");
        return productOrderRepository.findAll();
    }

    /**
     * Get all the productOrders with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductOrder> findAllWithEagerRelationships(Pageable pageable) {
        return productOrderRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one productOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductOrder> findOne(String id) {
        log.debug("Request to get ProductOrder : {}", id);
        return productOrderRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the productOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProductOrder : {}", id);
        productOrderRepository.deleteById(id);
    }
}
