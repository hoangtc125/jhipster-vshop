import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IShoppingCart } from 'app/shared/model/shopping-cart.model';
import { getEntities } from './shopping-cart.reducer';

export const ShoppingCart = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const shoppingCartList = useAppSelector(state => state.shoppingCart.entities);
  const loading = useAppSelector(state => state.shoppingCart.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="shopping-cart-heading" data-cy="ShoppingCartHeading">
        <Translate contentKey="vshopApp.shoppingCart.home.title">Shopping Carts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="vshopApp.shoppingCart.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/shopping-cart/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="vshopApp.shoppingCart.home.createLabel">Create new Shopping Cart</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {shoppingCartList && shoppingCartList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.placedDate">Placed Date</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.totalPrice">Total Price</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.paymentMethod">Payment Method</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.paymentReference">Payment Reference</Translate>
                </th>
                <th>
                  <Translate contentKey="vshopApp.shoppingCart.customerDetails">Customer Details</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shoppingCartList.map((shoppingCart, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/shopping-cart/${shoppingCart.id}`} color="link" size="sm">
                      {shoppingCart.id}
                    </Button>
                  </td>
                  <td>
                    {shoppingCart.placedDate ? <TextFormat type="date" value={shoppingCart.placedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`vshopApp.OrderStatus.${shoppingCart.status}`} />
                  </td>
                  <td>{shoppingCart.totalPrice}</td>
                  <td>
                    <Translate contentKey={`vshopApp.PaymentMethod.${shoppingCart.paymentMethod}`} />
                  </td>
                  <td>{shoppingCart.paymentReference}</td>
                  <td>
                    {shoppingCart.customerDetails ? (
                      <Link to={`/customer-details/${shoppingCart.customerDetails.id}`}>{shoppingCart.customerDetails.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/shopping-cart/${shoppingCart.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/shopping-cart/${shoppingCart.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/shopping-cart/${shoppingCart.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="vshopApp.shoppingCart.home.notFound">No Shopping Carts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ShoppingCart;
