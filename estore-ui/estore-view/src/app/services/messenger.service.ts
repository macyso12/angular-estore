import { Injectable } from '@angular/core';

import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessengerService {
  constructor() {}

  subject = new Subject();

  sendMsg(product: any) {
    //53.46
    console.log(product);
    this.subject.next(product); // Triggering an event
  }

  messages: string[] = [];

  add(message: string) {
    this.messages.push(message);
  }

  getMsg() {
    return this.subject.asObservable();
  }
}
