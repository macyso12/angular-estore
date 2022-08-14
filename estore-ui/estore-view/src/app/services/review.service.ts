import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { catchError, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Review } from '../models/review';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private reviewsUrl = 'http://localhost:8080/reviews';
  messages: string = '';

  // used on http.put()
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) { }

  getReviews(): Observable<Review[]> {
    return this.http
      .get<Review[]>(this.reviewsUrl)
      .pipe(catchError(this.handleError<Review[]>('getReviews', [])));
  }


  createReview(review: Review): Observable<Review> {
    return this.http
      .post<Review>(this.reviewsUrl, review, this.httpOptions)
      .pipe(
        tap((newReview: Review) =>
          console.log(`added review w/ id=${newReview.id}`)
        ),
        catchError(this.handleError<Review>('createReview'))
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
