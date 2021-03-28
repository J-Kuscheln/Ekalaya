import { Router } from '@angular/router';
import { SessionService } from './../service/session.service';
import { Member } from './../member/Member';
import { environment } from './../../environments/environment';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
/*
    {   "id":"13401548-df30-4a4c-8ee0-ad59f81b14ab",
        "firstName":"Rozaan Wiryanto",
        "lastName":"asfd",
        "photoUrl":null,
        "position":"asfasfd",
        "email":"asdfasdf",
        "phone":"asdfas",
        "birthday":"4567-03-12",
        "joinDate":"2021-02-22T23:46:36.000+00:00",
        "modifiedDate":"2021-02-22T23:46:36.000+00:00",
        "leadedProjects":[],
        "memberProjects":[],
        "finishedProjects":[]}
*/
export class EditProfileComponent implements OnInit {
  member:Member;

  @ViewChild("firstName")
  firstName:ElementRef
  @ViewChild("lastName")
  lastName:ElementRef
  @ViewChild("email")
  email:ElementRef
  @ViewChild("phone")
  phone:ElementRef
  @ViewChild("birthday")
  birthday:ElementRef
  @ViewChild("oldPassword")
  oldPassword:ElementRef
  @ViewChild("newPassword")
  newPassword:ElementRef


  baseUrl = environment.apiBaseUrl;
  constructor(private session:SessionService, private router:Router) { }

  ngOnInit(): void {
    this.session.myStatus$
    .subscribe(resp=>{
      console.log(resp);
      if(resp==null) {
        this.router.navigate(["/"]).then(()=>location.reload());
        return;
      }
      this.getMember(resp).then(respData=>this.member=respData).finally();
    })
    .unsubscribe;
  }

  getMember(id:String){
    return fetch(this.baseUrl+"/members/"+id, {credentials:'include'})
    .then(resp=>resp.json())
  }

  updateMember(){
    console.log("update member...")
    let status = document.querySelector("div #status");
    if(this.isValidInput(this.phone)){
      if(this.isAuthorized()){
        //update new email and new password
        status.textContent = "Changes saved!";
        status.setAttribute("style", "color:green");
        return;
      }

      this.oldPassword.nativeElement.className="form-control is-invalid";
      document.querySelector("#oldPassword-invalid-feedback").textContent = "Wrong password!"
    }
  }

  hash(email:string, password:string):string{
    let str =  email + password;
    var hash = 0, i, chr;
    for (i = 0; i < str.length; i++) {
      chr   = str.charCodeAt(i);
      hash  = ((hash << 5) - hash) + chr;
      hash |= 0; // Convert to 32bit integer
    }
    return hash.toString();
  }

  isAuthorized(){
    //fetch to checkAuth
    let url = environment.apiBaseUrl + "/auth/checkAuth"
    let oldEmail = this.member.email;
    let oldPassword = this.oldPassword.nativeElement.value;
    let hashValue = this.hash(<string> oldEmail,oldPassword);

    let body = `{"email":"` + oldEmail + `", "password":"` + hashValue + `"}`

    return fetch(url,{method:'post', credentials:'include', headers:{'Content-Type': 'application/json'}, body:body})
    .then(resp=>resp);
  }

  isValidInput(element:ElementRef):boolean{
    let number:string = element.nativeElement.value
    var code, i, len;

    //phone number should be between 10-15 or completely blank
    if(number.length==0){element.nativeElement.className="form-control"; return true;}
    if((number.length<10 || (number.length>15))) {
      element.nativeElement.className="form-control is-invalid";
      return false;
    }

    for (i = 0, len = number.length; i < len; i++) {
      code = number.charCodeAt(i);

      let isNumeric = (code>=48 && code<=57);
      let isPlusOrZero = ((code==43 || code==48));
      
      if ((i!=0 && !isNumeric) || (i==0 && !isPlusOrZero)) // numeric (0-9
      { // lower alpha (a-z)
        element.nativeElement.className="form-control is-invalid";
        return false;
      }
    }
    //if(number.charAt(0) == '+')number = number.slice(3);
    //if(number.charAt(0) == "0")number = number.substr(1);

    //numverify.com
    /*
    let API_KEY = "9f6b2b04423b31a975056d6e084a4e22";
    let url = "http://apilayer.net/api/validate?access_key="+API_KEY+"&number="+number+"&country_code=&format=1";
    fetch(url)
    .then(response=>response.json())
    .then(data=>console.log("number validation: ", data['valid']));
    */
    
    element.nativeElement.className="form-control is-valid";
    return true;
  }


}
