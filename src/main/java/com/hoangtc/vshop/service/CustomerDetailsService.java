package com.hoangtc.vshop.service;

import com.hoangtc.vshop.domain.CustomerDetails;
import com.hoangtc.vshop.repository.CustomerDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link CustomerDetails}.
 */
@Service
public class CustomerDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsService.class);

    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsService(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    /**
     * Save a customerDetails.
     *
     * @param customerDetails the entity to save.
     * @return the persisted entity.
     */
    public CustomerDetails save(CustomerDetails customerDetails) {
        log.debug("Request to save CustomerDetails : {}", customerDetails);
        return customerDetailsRepository.save(customerDetails);
    }

    /**
     * Update a customerDetails.
     *
     * @param customerDetails the entity to save.
     * @return the persisted entity.
     */
    public CustomerDetails update(CustomerDetails customerDetails) {
        log.debug("Request to update CustomerDetails : {}", customerDetails);
        return customerDetailsRepository.save(customerDetails);
    }

    /**
     * Partially update a customerDetails.
     *
     * @param customerDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerDetails> partialUpdate(CustomerDetails customerDetails) {
        log.debug("Request to partially update CustomerDetails : {}", customerDetails);

        return customerDetailsRepository
            .findById(customerDetails.getId())
            .map(existingCustomerDetails -> {
                if (customerDetails.getGender() != null) {
                    existingCustomerDetails.setGender(customerDetails.getGender());
                }
                if (customerDetails.getPhone() != null) {
                    existingCustomerDetails.setPhone(customerDetails.getPhone());
                }
                if (customerDetails.getAddressLine1() != null) {
                    existingCustomerDetails.setAddressLine1(customerDetails.getAddressLine1());
                }
                if (customerDetails.getAddressLine2() != null) {
                    existingCustomerDetails.setAddressLine2(customerDetails.getAddressLine2());
                }
                if (customerDetails.getCity() != null) {
                    existingCustomerDetails.setCity(customerDetails.getCity());
                }
                if (customerDetails.getCountry() != null) {
                    existingCustomerDetails.setCountry(customerDetails.getCountry());
                }

                return existingCustomerDetails;
            })
            .map(customerDetailsRepository::save);
    }

    /**
     * Get all the customerDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CustomerDetails> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerDetails");
        return customerDetailsRepository.findAll(pageable);
    }

    /**
     * Get all the customerDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CustomerDetails> findAllWithEagerRelationships(Pageable pageable) {
        return customerDetailsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one customerDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CustomerDetails> findOne(String id) {
        log.debug("Request to get CustomerDetails : {}", id);
        return customerDetailsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the customerDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CustomerDetails : {}", id);
        customerDetailsRepository.deleteById(id);
    }
}
