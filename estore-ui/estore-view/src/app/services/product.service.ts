import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { catchError, tap } from 'rxjs/operators';

import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private productsUrl = 'http://localhost:8080/products';
  messages: string = '';

  // used on http.put()
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.productsUrl)
      .pipe(catchError(this.handleError<Product[]>('getProducts', [])));
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      tap((_) => console.log(`fetched product id=${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  searchProducts(term: string): Observable<Product[]> {
    if (!term.trim()) return of([]);
    return this.http.get<Product[]>(`${this.productsUrl}/?name=${term}`).pipe(
      tap((_) => console.log(`found products matching "${term}"`)),
      catchError(this.handleError<Product[]>('searchProducts', []))
    );
  }

  addProduct(product: Product): Observable<Product> {
    return this.http
      .post<Product>(this.productsUrl, product, this.httpOptions)
      .pipe(
        tap((newProduct: Product) =>
          console.log(`added product w/ id=${newProduct.id}`)
        ),
        catchError(this.handleError<Product>('addProduct'))
      );
  }

  updateProduct(product: Product): Observable<Product> {
    return this.http
      .put<Product>(this.productsUrl, product, this.httpOptions)
      .pipe(
        tap((_) => console.log(`updated product id=${product.id}`)),
        catchError(this.handleError<Product>('updateProduct'))
      );
  }

  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap((_) => console.log(`deleted product id=${id}`)),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  handleError<T>(_operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${_operation} failed: ${error.message}`);
      // this one can be commented out
      this.messages = `${_operation} failed: ${error.message}`;
      return of(result as T);
    };
  }
}
