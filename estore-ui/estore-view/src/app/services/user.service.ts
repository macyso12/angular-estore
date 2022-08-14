import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { User, UserState } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = 'http://localhost:8080/users';
  messages: string = '';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.usersUrl)
      .pipe(catchError(this.handleError<User[]>('getUsers', [])));
  }

  getUser(id: number): Observable<User> {
    const url = `${this.usersUrl}/${id}`;
    return this.http.get<User>(url).pipe(
      tap((_) => console.log(`fetched user id=${id}`)),
      catchError(this.handleError<User>(`getUser id=${id}`))
    );
  }

  searchUsers(term: string): Observable<User[]> {
    if (!term.trim()) return of([]);
    return this.http.
      get<User[]>(`${this.usersUrl}/?username=${term}`)
      .pipe(
        tap((_) => console.log(`found users matching "${term}"`)),
        catchError(this.handleError<User[]>('searchUsers', []))
      );
  }

  createUser(user: User): Observable<User> {
    return this.http
      .post<User>(this.usersUrl, user, this.httpOptions)
      .pipe(
        tap((newUser: User) => console.log(`added user w/ id=${newUser.id}`)),
        catchError(this.handleError<User>('createUser'))
      );
  }

  // Check if the user is valid before updating the user
  updateUser(user: User): Observable<User> {
    return this.http
      .put<User>(this.usersUrl, user, this.httpOptions)
      .pipe(
        tap((_) => console.log(`updated user id=${user.id}`)),
        catchError(this.handleError<User>('updateUser'))
      );
  }

  deleteUser(id: number): Observable<User> {
    const url = `${this.usersUrl}/${id}`;
    return this.http.delete<User>(url, this.httpOptions).pipe(
      tap((_) => console.log(`deleted user id=${id}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  loginUser(user: User): Observable<User> {
    user.userState = UserState.LOGGED_IN;
    this.updateUser(user);
    return this.http.put<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap((_) => console.log(`login user id=${user.id}\ncurrent user state is ${user.userState}`)),
      catchError(this.handleError<User>('loginUser'))
    );
  }

  logoutUser(user: User): Observable<User> {
    user.userState = UserState.LOGGED_OUT;
    this.updateUser(user);
    return this.http.put<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap((_) => console.log(`updated user id=${user.id}\ncurrent user state is ${user.userState}`)),
      catchError(this.handleError<User>('updateUser'))
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
