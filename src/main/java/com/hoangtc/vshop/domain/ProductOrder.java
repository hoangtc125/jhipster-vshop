package com.hoangtc.vshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ProductOrder.
 */
@Document(collection = "product_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Min(value = 0)
    @Field("quantity")
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Field("total_price")
    private BigDecimal totalPrice;

    @DBRef
    @Field("product")
    @JsonIgnoreProperties(value = { "productCategory" }, allowSetters = true)
    private Product product;

    @DBRef
    @Field("cart")
    @JsonIgnoreProperties(value = { "orders", "customerDetails" }, allowSetters = true)
    private ShoppingCart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProductOrder id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public ProductOrder quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public ProductOrder totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductOrder product(Product product) {
        this.setProduct(product);
        return this;
    }

    public ShoppingCart getCart() {
        return this.cart;
    }

    public void setCart(ShoppingCart shoppingCart) {
        this.cart = shoppingCart;
    }

    public ProductOrder cart(ShoppingCart shoppingCart) {
        this.setCart(shoppingCart);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOrder)) {
            return false;
        }
        return id != null && id.equals(((ProductOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOrder{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
