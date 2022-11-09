import product from 'app/entities/product/product.reducer';
import productCategory from 'app/entities/product-category/product-category.reducer';
import customerDetails from 'app/entities/customer-details/customer-details.reducer';
import shoppingCart from 'app/entities/shopping-cart/shopping-cart.reducer';
import productOrder from 'app/entities/product-order/product-order.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  product,
  productCategory,
  customerDetails,
  shoppingCart,
  productOrder,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
