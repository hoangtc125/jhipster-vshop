package com.hoangtc.vshop.web.rest;

import static com.hoangtc.vshop.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hoangtc.vshop.IntegrationTest;
import com.hoangtc.vshop.domain.CustomerDetails;
import com.hoangtc.vshop.domain.ShoppingCart;
import com.hoangtc.vshop.domain.enumeration.OrderStatus;
import com.hoangtc.vshop.domain.enumeration.PaymentMethod;
import com.hoangtc.vshop.repository.ShoppingCartRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ShoppingCartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShoppingCartResourceIT {

    private static final Instant DEFAULT_PLACED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.COMPLETED;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.PAID;

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(1);

    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.IDEAL;

    private static final String DEFAULT_PAYMENT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_REFERENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shopping-carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private MockMvc restShoppingCartMockMvc;

    private ShoppingCart shoppingCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCart createEntity() {
        ShoppingCart shoppingCart = new ShoppingCart()
            .placedDate(DEFAULT_PLACED_DATE)
            .status(DEFAULT_STATUS)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .paymentReference(DEFAULT_PAYMENT_REFERENCE);
        // Add required entity
        CustomerDetails customerDetails;
        customerDetails = CustomerDetailsResourceIT.createEntity();
        customerDetails.setId("fixed-id-for-tests");
        shoppingCart.setCustomerDetails(customerDetails);
        return shoppingCart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCart createUpdatedEntity() {
        ShoppingCart shoppingCart = new ShoppingCart()
            .placedDate(UPDATED_PLACED_DATE)
            .status(UPDATED_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE);
        // Add required entity
        CustomerDetails customerDetails;
        customerDetails = CustomerDetailsResourceIT.createUpdatedEntity();
        customerDetails.setId("fixed-id-for-tests");
        shoppingCart.setCustomerDetails(customerDetails);
        return shoppingCart;
    }

    @BeforeEach
    public void initTest() {
        shoppingCartRepository.deleteAll();
        shoppingCart = createEntity();
    }

    @Test
    void createShoppingCart() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();
        // Create the ShoppingCart
        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getPlacedDate()).isEqualTo(DEFAULT_PLACED_DATE);
        assertThat(testShoppingCart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShoppingCart.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
        assertThat(testShoppingCart.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testShoppingCart.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
    }

    @Test
    void createShoppingCartWithExistingId() throws Exception {
        // Create the ShoppingCart with an existing ID
        shoppingCart.setId("existing_id");

        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkPlacedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartRepository.findAll().size();
        // set the field null
        shoppingCart.setPlacedDate(null);

        // Create the ShoppingCart, which fails.

        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartRepository.findAll().size();
        // set the field null
        shoppingCart.setStatus(null);

        // Create the ShoppingCart, which fails.

        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartRepository.findAll().size();
        // set the field null
        shoppingCart.setTotalPrice(null);

        // Create the ShoppingCart, which fails.

        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartRepository.findAll().size();
        // set the field null
        shoppingCart.setPaymentMethod(null);

        // Create the ShoppingCart, which fails.

        restShoppingCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        // Get all the shoppingCartList
        restShoppingCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId())))
            .andExpect(jsonPath("$.[*].placedDate").value(hasItem(DEFAULT_PLACED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE))))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE)));
    }

    @Test
    void getShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        // Get the shoppingCart
        restShoppingCartMockMvc
            .perform(get(ENTITY_API_URL_ID, shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCart.getId()))
            .andExpect(jsonPath("$.placedDate").value(DEFAULT_PLACED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalPrice").value(sameNumber(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.paymentReference").value(DEFAULT_PAYMENT_REFERENCE));
    }

    @Test
    void getNonExistingShoppingCart() throws Exception {
        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart
        ShoppingCart updatedShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).get();
        updatedShoppingCart
            .placedDate(UPDATED_PLACED_DATE)
            .status(UPDATED_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE);

        restShoppingCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedShoppingCart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedShoppingCart))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getPlacedDate()).isEqualTo(UPDATED_PLACED_DATE);
        assertThat(testShoppingCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingCart.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testShoppingCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testShoppingCart.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    void putNonExistingShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingCart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingCart))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingCart))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShoppingCartWithPatch() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart using partial update
        ShoppingCart partialUpdatedShoppingCart = new ShoppingCart();
        partialUpdatedShoppingCart.setId(shoppingCart.getId());

        partialUpdatedShoppingCart.status(UPDATED_STATUS).paymentMethod(UPDATED_PAYMENT_METHOD);

        restShoppingCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingCart))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getPlacedDate()).isEqualTo(DEFAULT_PLACED_DATE);
        assertThat(testShoppingCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingCart.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
        assertThat(testShoppingCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testShoppingCart.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
    }

    @Test
    void fullUpdateShoppingCartWithPatch() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart using partial update
        ShoppingCart partialUpdatedShoppingCart = new ShoppingCart();
        partialUpdatedShoppingCart.setId(shoppingCart.getId());

        partialUpdatedShoppingCart
            .placedDate(UPDATED_PLACED_DATE)
            .status(UPDATED_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE);

        restShoppingCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingCart))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getPlacedDate()).isEqualTo(UPDATED_PLACED_DATE);
        assertThat(testShoppingCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingCart.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testShoppingCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testShoppingCart.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    void patchNonExistingShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shoppingCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingCart))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingCart))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();
        shoppingCart.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shoppingCart))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.save(shoppingCart);

        int databaseSizeBeforeDelete = shoppingCartRepository.findAll().size();

        // Delete the shoppingCart
        restShoppingCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, shoppingCart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
