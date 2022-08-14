import { Component, OnInit, Input } from '@angular/core';
import { Product } from 'src/app/models/product';
import { MessengerService } from 'src/app/services/messenger.service';
import { WishlistService } from 'src/app/services/wishlist.service';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css'],
})
export class ProductItemComponent implements OnInit {
  @Input()
  productItem!: Product;

  addToWishlist: boolean = false;

  constructor(
    private msg: MessengerService,
    private wishlistService: WishlistService
  ) {}

  ngOnInit() {}

  handleAddToCart() {
    this.msg.sendMsg(this.productItem);
  }

  handleAddToWishlist() {
    this.wishlistService.addToWishlist(this.productItem.id).subscribe(() => {
      this.addToWishlist = true;
    });
    this.addToWishlist = true;
  }

  handleRemoveFromWishlist() {
    this.wishlistService
      .removeFromWishlist(this.productItem.id)
      .subscribe(() => {
        this.addToWishlist = false;
      });
    this.addToWishlist = false;
  }
}
