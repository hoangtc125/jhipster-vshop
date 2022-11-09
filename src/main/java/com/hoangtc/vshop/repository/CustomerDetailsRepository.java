package com.hoangtc.vshop.repository;

import com.hoangtc.vshop.domain.CustomerDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CustomerDetails entity.
 */
@Repository
public interface CustomerDetailsRepository extends MongoRepository<CustomerDetails, String> {
    @Query("{}")
    Page<CustomerDetails> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<CustomerDetails> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<CustomerDetails> findOneWithEagerRelationships(String id);
}
