import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-category">
        <Translate contentKey="global.menu.entities.productCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer-details">
        <Translate contentKey="global.menu.entities.customerDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/shopping-cart">
        <Translate contentKey="global.menu.entities.shoppingCart" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-order">
        <Translate contentKey="global.menu.entities.productOrder" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
