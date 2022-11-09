package com.hoangtc.vshop.repository;

import com.hoangtc.vshop.domain.ProductOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ProductOrder entity.
 */
@Repository
public interface ProductOrderRepository extends MongoRepository<ProductOrder, String> {
    @Query("{}")
    Page<ProductOrder> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ProductOrder> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ProductOrder> findOneWithEagerRelationships(String id);
}
