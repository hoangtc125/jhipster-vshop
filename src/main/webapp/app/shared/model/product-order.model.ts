import { IProduct } from 'app/shared/model/product.model';
import { IShoppingCart } from 'app/shared/model/shopping-cart.model';

export interface IProductOrder {
  id?: string;
  quantity?: number;
  totalPrice?: number;
  product?: IProduct;
  cart?: IShoppingCart;
}

export const defaultValue: Readonly<IProductOrder> = {};
