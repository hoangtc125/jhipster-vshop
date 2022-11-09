import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CustomerDetails from './customer-details';
import CustomerDetailsDetail from './customer-details-detail';
import CustomerDetailsUpdate from './customer-details-update';
import CustomerDetailsDeleteDialog from './customer-details-delete-dialog';

const CustomerDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CustomerDetails />} />
    <Route path="new" element={<CustomerDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<CustomerDetailsDetail />} />
      <Route path="edit" element={<CustomerDetailsUpdate />} />
      <Route path="delete" element={<CustomerDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CustomerDetailsRoutes;
