import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  myStatus$: Observable<string>;
  myUserFirstName$: Observable<string>;
  myUserLastName$: Observable<string>;

  private sessionStat = new Subject<string>();
  private sessionUserFirstName = new Subject<string>();
  private sessionUserLastName = new Subject<string>();

  constructor() {
      this.myStatus$ = this.sessionStat.asObservable();
      this.myUserFirstName$ = this.sessionUserFirstName.asObservable();
      this.myUserLastName$ = this.sessionUserLastName.asObservable();
  }

  myStatus(data:string) {
    this.sessionStat.next(data);
  }
  
  myUserName(firstName:string, lastName:string){
    this.sessionUserFirstName.next(firstName);
    this.sessionUserLastName.next(lastName);
  }
}
