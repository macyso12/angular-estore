import { Component, OnInit, Input } from '@angular/core';
import { MessengerService } from 'src/app/services/messenger.service';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css'],
})
export class CartItemComponent implements OnInit {
  @Input() cartItem: any;
  cartItems: any[] = [];
  cartTotal = 0;

  constructor(private msg: MessengerService) {}

  ngOnInit() {}

  handleRemoveFromCart() {
    this.cartItem.qty--;
    this.cartTotal -= this.cartItem.price;
    if (this.cartItem.qty === 0) {
      //remove item from cart
    }
  }
}
