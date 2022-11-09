package com.hoangtc.vshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hoangtc.vshop.domain.enumeration.OrderStatus;
import com.hoangtc.vshop.domain.enumeration.PaymentMethod;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ShoppingCart.
 */
@Document(collection = "shopping_cart")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("placed_date")
    private Instant placedDate;

    @NotNull
    @Field("status")
    private OrderStatus status;

    @NotNull
    @DecimalMin(value = "0")
    @Field("total_price")
    private BigDecimal totalPrice;

    @NotNull
    @Field("payment_method")
    private PaymentMethod paymentMethod;

    @Field("payment_reference")
    private String paymentReference;

    @DBRef
    @Field("order")
    @JsonIgnoreProperties(value = { "product", "cart" }, allowSetters = true)
    private Set<ProductOrder> orders = new HashSet<>();

    @DBRef
    @Field("customerDetails")
    @JsonIgnoreProperties(value = { "user", "carts" }, allowSetters = true)
    private CustomerDetails customerDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ShoppingCart id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getPlacedDate() {
        return this.placedDate;
    }

    public ShoppingCart placedDate(Instant placedDate) {
        this.setPlacedDate(placedDate);
        return this;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public ShoppingCart status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public ShoppingCart totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public ShoppingCart paymentMethod(PaymentMethod paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentReference() {
        return this.paymentReference;
    }

    public ShoppingCart paymentReference(String paymentReference) {
        this.setPaymentReference(paymentReference);
        return this;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Set<ProductOrder> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<ProductOrder> productOrders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setCart(null));
        }
        if (productOrders != null) {
            productOrders.forEach(i -> i.setCart(this));
        }
        this.orders = productOrders;
    }

    public ShoppingCart orders(Set<ProductOrder> productOrders) {
        this.setOrders(productOrders);
        return this;
    }

    public ShoppingCart addOrder(ProductOrder productOrder) {
        this.orders.add(productOrder);
        productOrder.setCart(this);
        return this;
    }

    public ShoppingCart removeOrder(ProductOrder productOrder) {
        this.orders.remove(productOrder);
        productOrder.setCart(null);
        return this;
    }

    public CustomerDetails getCustomerDetails() {
        return this.customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public ShoppingCart customerDetails(CustomerDetails customerDetails) {
        this.setCustomerDetails(customerDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        return id != null && id.equals(((ShoppingCart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingCart{" +
            "id=" + getId() +
            ", placedDate='" + getPlacedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", paymentReference='" + getPaymentReference() + "'" +
            "}";
    }
}
