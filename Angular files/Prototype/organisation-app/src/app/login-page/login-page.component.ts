import { environment } from './../../environments/environment';
import { Router, RouterModule } from '@angular/router';
import { SessionService } from './../service/session.service';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loggedIn = false;
  failedAttempt = false;
  @ViewChild("email") 
  email?:ElementRef
  @ViewChild("password")
  password?: ElementRef
  constructor(private session: SessionService, 
    private router:Router
    ) {
  }

  ngOnInit(): void {
    this.session.myStatus$
    .subscribe(sessionStat=>{
      if(sessionStat!=null && this.router.url == "/login"){
        this.router.navigate(["/"]);
      }
    });
  }

  hashCode() {
    console.log("hashing!")
    let email:string = this.email?.nativeElement.value;
    let password:string = this.password?.nativeElement.value;
    let str =  email + password;
    var hash = 0, i, chr;
    for (i = 0; i < str.length; i++) {
      chr   = str.charCodeAt(i);
      hash  = ((hash << 5) - hash) + chr;
      hash |= 0; // Convert to 32bit integer
    }
    /*
    console.log("email: " + email)
    console.log("pass: " + password)
    console.log("hash: " + hash)
    */
    let jsonBody:string = `{"email":"` + email + `", "password":"` + hash + `"}`
    console.log(jsonBody);
    this.login(jsonBody).then(response=>{
      if(response){
        window.location.reload()
        this.failedAttempt=false;
      }
      else this.failedAttempt=true;

      console.log("failed attempt: ", this.failedAttempt);
    });
  }

  login(body:string){
    let baseUrl = environment.apiBaseUrl
    return fetch(baseUrl+"/auth/login",{
      method:'POST',
      headers:{'Content-Type': 'application/json'},
      credentials:'include',
      body: body
    })
    .then(response=>response.json())
  }
}
