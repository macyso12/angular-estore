import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/product';
import { Location } from '@angular/common';

import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.css'],
})
export class AdminEditComponent implements OnInit {
  product: Product | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService
      .getProduct(id)
      .subscribe((product) => (this.product = product));
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService
        .updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }
}
