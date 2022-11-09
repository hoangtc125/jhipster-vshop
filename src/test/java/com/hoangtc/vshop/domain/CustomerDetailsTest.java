package com.hoangtc.vshop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hoangtc.vshop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerDetails.class);
        CustomerDetails customerDetails1 = new CustomerDetails();
        customerDetails1.setId("id1");
        CustomerDetails customerDetails2 = new CustomerDetails();
        customerDetails2.setId(customerDetails1.getId());
        assertThat(customerDetails1).isEqualTo(customerDetails2);
        customerDetails2.setId("id2");
        assertThat(customerDetails1).isNotEqualTo(customerDetails2);
        customerDetails1.setId(null);
        assertThat(customerDetails1).isNotEqualTo(customerDetails2);
    }
}
