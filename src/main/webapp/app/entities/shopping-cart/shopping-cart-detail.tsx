import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './shopping-cart.reducer';

export const ShoppingCartDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shoppingCartEntity = useAppSelector(state => state.shoppingCart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shoppingCartDetailsHeading">
          <Translate contentKey="vshopApp.shoppingCart.detail.title">ShoppingCart</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{shoppingCartEntity.id}</dd>
          <dt>
            <span id="placedDate">
              <Translate contentKey="vshopApp.shoppingCart.placedDate">Placed Date</Translate>
            </span>
          </dt>
          <dd>
            {shoppingCartEntity.placedDate ? (
              <TextFormat value={shoppingCartEntity.placedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="vshopApp.shoppingCart.status">Status</Translate>
            </span>
          </dt>
          <dd>{shoppingCartEntity.status}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="vshopApp.shoppingCart.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{shoppingCartEntity.totalPrice}</dd>
          <dt>
            <span id="paymentMethod">
              <Translate contentKey="vshopApp.shoppingCart.paymentMethod">Payment Method</Translate>
            </span>
          </dt>
          <dd>{shoppingCartEntity.paymentMethod}</dd>
          <dt>
            <span id="paymentReference">
              <Translate contentKey="vshopApp.shoppingCart.paymentReference">Payment Reference</Translate>
            </span>
          </dt>
          <dd>{shoppingCartEntity.paymentReference}</dd>
          <dt>
            <Translate contentKey="vshopApp.shoppingCart.customerDetails">Customer Details</Translate>
          </dt>
          <dd>{shoppingCartEntity.customerDetails ? shoppingCartEntity.customerDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/shopping-cart" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shopping-cart/${shoppingCartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShoppingCartDetail;
