import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';

import { Observable, Subject } from 'rxjs';
import { ProductService } from 'src/app/services/product.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css'],
})
export class FiltersComponent implements OnInit {
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  constructor(private productService: ProductService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.products$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.searchProducts(term))
    );
  }
}
