import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  key: string = "currentUser";
  constructor() { }

  public saveCurrentUser(user: User) {
    localStorage.setItem(this.key, JSON.stringify(user));
  }

  public isExistUser(): boolean {
    return localStorage.getItem('currentUser') != null;
  }

  public getCurrentUser():User | null {
    const user = localStorage.getItem(this.key);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  public clearCurrentUser() {
    localStorage.removeItem(this.key);
  }
}
