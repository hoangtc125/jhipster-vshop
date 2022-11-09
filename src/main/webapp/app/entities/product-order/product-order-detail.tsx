import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-order.reducer';

export const ProductOrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productOrderEntity = useAppSelector(state => state.productOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productOrderDetailsHeading">
          <Translate contentKey="vshopApp.productOrder.detail.title">ProductOrder</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productOrderEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="vshopApp.productOrder.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{productOrderEntity.quantity}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="vshopApp.productOrder.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{productOrderEntity.totalPrice}</dd>
          <dt>
            <Translate contentKey="vshopApp.productOrder.product">Product</Translate>
          </dt>
          <dd>{productOrderEntity.product ? productOrderEntity.product.name : ''}</dd>
          <dt>
            <Translate contentKey="vshopApp.productOrder.cart">Cart</Translate>
          </dt>
          <dd>{productOrderEntity.cart ? productOrderEntity.cart.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-order/${productOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductOrderDetail;
