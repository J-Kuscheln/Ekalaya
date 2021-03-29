import { environment } from './../environments/environment';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'organisation-app';
  logInOut:string = "";
  logged:boolean = false;
  @ViewChild("email") 
  email?:ElementRef
  @ViewChild("password")
  password?: ElementRef
  userLastName:string="";
  private baseUrl = environment.apiBaseUrl
  constructor(private session:SessionService, public router: Router){}
  
  ngOnInit(){
    this.checkSession()
    .then(response=>{
      //console.log("respose: ", response);
      this.session.myStatus(response["USER_ID"]);
      if(response["USER_ID"]==null) this.loggedIn(false);
      else {
        //update loggedIn status
        this.loggedIn(true);
        //update session username
        this.getUserInfo(response["USER_ID"])
        .then(response=>{
          //console.log("user Infos: ", response)
          this.session.myUserName(response["firstName"], response["lastName"])
        });
      }
    })
    this.session.myUserLastName$
    .subscribe(resp=>this.userLastName=resp)
    .unsubscribe;
    this.session.myStatus$
    .subscribe(resp=>{
      if(resp==null) this.loggedIn(false);
      else this.loggedIn(true);
    })
  }
  async checkSession(){
    console.log("checking session....")
    return fetch(this.baseUrl+"/auth/session",{credentials:'include'}).then(response=>response.json())
    .then(data=>data);
  }

  getSession(){
    this.checkSession().then(response=>response.json())
  }

  getUserInfo(id:string){
    console.log("get User Info...")
    return fetch(this.baseUrl+"/members/"+id,{credentials:'include'}).then(response=>response.json())
    .then(data=>data);
  }

  loggedIn(status:boolean){
    if (!status){ //if not logged in
      this.logInOut="Login";
      this.logged=false;
    }
    else{
      this.logInOut="Logout";
      this.logged=true;
    }
  }

  logout(){
    this.checkSession()
    .then(response=>{
      //console.log("session: ", response["USER_ID"]);
      this.session.myStatus(response["USER_ID"]);
      if(response["USER_ID"]==null) console.log("USER_ID:", response["USER_ID"])
      else {
        //console.log("here??")
        fetch(this.baseUrl+"/auth/logout",{credentials:'include'})
        .then(response=>response).then(data=>{
          if(data) {
            this.loggedIn(false)
            location.reload();
          }
          else console.log("something is wrong");
        })
      }
    })
  }
}
