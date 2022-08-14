import { Component, OnInit } from '@angular/core';
import { MessengerService } from 'src/app/services/messenger.service';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  /**
   * {id: 1, productId: 1, productName: 'Test 1', qty: 4, price: 100}
   */
  //cartItems = [];

  cartItems: any[] = [];

  cartTotal = 0;

  constructor(private msg: MessengerService) {}

  //ERROR -> pushing items to cart to populate cart
  //video time at around 59 mins
  ngOnInit() {
    this.msg.getMsg().subscribe((product: any) => {
      this.addProductToCart(product);
    });
  }

  addProductToCart(product: Product) {
    let productExists = false;

    for (let i in this.cartItems) {
      if (this.cartItems[i]['productId'] === product.id) {
        productExists = true;
        this.cartItems[i]['qty']++;
        break;
      }
    }

    if (!productExists) {
      this.cartItems.push({
        productId: product.id,
        productName: product.name,
        qty: 1,
        price: product.price,
      });
    }

    this.cartTotal = 0;

    this.cartItems.forEach((item) => {
      this.cartTotal += item['qty'] * item['price'];
    });
  }

  removeProductToCart(product: Product) {
    this.cartItems.forEach((item) => {
      this.cartTotal -= item['qty'] * item['price'];
    });
  }

  checkOutCart() {
    this.cartTotal = 0;
    this.cartItems = [];
  }
}
