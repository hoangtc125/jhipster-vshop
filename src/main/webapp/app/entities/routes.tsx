import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Product from './product';
import ProductCategory from './product-category';
import CustomerDetails from './customer-details';
import ShoppingCart from './shopping-cart';
import ProductOrder from './product-order';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="product/*" element={<Product />} />
        <Route path="product-category/*" element={<ProductCategory />} />
        <Route path="customer-details/*" element={<CustomerDetails />} />
        <Route path="shopping-cart/*" element={<ShoppingCart />} />
        <Route path="product-order/*" element={<ProductOrder />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
