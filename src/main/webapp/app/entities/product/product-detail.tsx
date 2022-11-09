import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="vshopApp.product.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="vshopApp.product.name">Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="vshopApp.product.description">Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="vshopApp.product.price">Price</Translate>
            </span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="productSize">
              <Translate contentKey="vshopApp.product.productSize">Product Size</Translate>
            </span>
          </dt>
          <dd>{productEntity.productSize}</dd>
          <dt>
            <Translate contentKey="vshopApp.product.productCategory">Product Category</Translate>
          </dt>
          <dd>{productEntity.productCategory ? productEntity.productCategory.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
      <Col md="4">
        <dt>
          <span id="image">
            <Translate contentKey="vshopApp.product.image">Image</Translate>
          </span>
        </dt>
        <dd>
          {productEntity.image ? (
            <div>
              {productEntity.imageContentType ? (
                <a onClick={openFile(productEntity.imageContentType, productEntity.image)}>
                  <img src={`data:${productEntity.imageContentType};base64,${productEntity.image}`} style={{ maxWidth: '100%' }} />
                </a>
              ) : null}
              <span>
                {productEntity.imageContentType}, {byteSize(productEntity.image)}
              </span>
            </div>
          ) : null}
        </dd>
      </Col>
    </Row>
  );
};

export default ProductDetail;
