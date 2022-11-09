import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer-details.reducer';

export const CustomerDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerDetailsEntity = useAppSelector(state => state.customerDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsDetailsHeading">
          <Translate contentKey="vshopApp.customerDetails.detail.title">CustomerDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.id}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="vshopApp.customerDetails.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.gender}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="vshopApp.customerDetails.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.phone}</dd>
          <dt>
            <span id="addressLine1">
              <Translate contentKey="vshopApp.customerDetails.addressLine1">Address Line 1</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.addressLine1}</dd>
          <dt>
            <span id="addressLine2">
              <Translate contentKey="vshopApp.customerDetails.addressLine2">Address Line 2</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.addressLine2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="vshopApp.customerDetails.city">City</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.city}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="vshopApp.customerDetails.country">Country</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.country}</dd>
          <dt>
            <Translate contentKey="vshopApp.customerDetails.user">User</Translate>
          </dt>
          <dd>{customerDetailsEntity.user ? customerDetailsEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-details/${customerDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetailsDetail;
