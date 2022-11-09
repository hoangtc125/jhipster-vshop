package com.hoangtc.vshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hoangtc.vshop.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A CustomerDetails.
 */
@Document(collection = "customer_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("gender")
    private Gender gender;

    @NotNull
    @Field("phone")
    private String phone;

    @NotNull
    @Field("address_line_1")
    private String addressLine1;

    @Field("address_line_2")
    private String addressLine2;

    @NotNull
    @Field("city")
    private String city;

    @NotNull
    @Field("country")
    private String country;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("cart")
    @JsonIgnoreProperties(value = { "orders", "customerDetails" }, allowSetters = true)
    private Set<ShoppingCart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CustomerDetails id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return this.gender;
    }

    public CustomerDetails gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public CustomerDetails phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public CustomerDetails addressLine1(String addressLine1) {
        this.setAddressLine1(addressLine1);
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public CustomerDetails addressLine2(String addressLine2) {
        this.setAddressLine2(addressLine2);
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return this.city;
    }

    public CustomerDetails city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public CustomerDetails country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomerDetails user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<ShoppingCart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<ShoppingCart> shoppingCarts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setCustomerDetails(null));
        }
        if (shoppingCarts != null) {
            shoppingCarts.forEach(i -> i.setCustomerDetails(this));
        }
        this.carts = shoppingCarts;
    }

    public CustomerDetails carts(Set<ShoppingCart> shoppingCarts) {
        this.setCarts(shoppingCarts);
        return this;
    }

    public CustomerDetails addCart(ShoppingCart shoppingCart) {
        this.carts.add(shoppingCart);
        shoppingCart.setCustomerDetails(this);
        return this;
    }

    public CustomerDetails removeCart(ShoppingCart shoppingCart) {
        this.carts.remove(shoppingCart);
        shoppingCart.setCustomerDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDetails)) {
            return false;
        }
        return id != null && id.equals(((CustomerDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDetails{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
