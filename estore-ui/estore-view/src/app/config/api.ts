import { environment } from 'src/environments/environment';

export const baseUrl = environment.production
  ? 'http://api.shoppingcart.com'
  : 'http://localhost/4200';

export const wishlistUrl = baseUrl + '/wishlist';
