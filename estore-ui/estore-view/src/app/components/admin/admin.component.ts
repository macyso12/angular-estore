import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';

import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  @Input()
  products: Product[] = [];
  productList: Product[] = [];
  product: Product | undefined;

  model: any = {};

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  getProducts(): void {
    this.productService
      .getProducts()
      .subscribe((products) => (this.products = products));
  }

  add(
    name: string,
    description: string,
    price: string,
    imageURL: string
  ): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this.productService
      .addProduct({ name, description, price, imageURL } as unknown as Product)
      .subscribe((product) => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter((h) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

  ngOnInit(): void {
    this.getProducts();
  }
}
