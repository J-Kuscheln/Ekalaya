import { Member } from './../member/Member';
import { Project } from './../project/Project';
import { SessionService } from './../services/session.service';
import { Router } from '@angular/router';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss']
})
export class EditProjectComponent implements OnInit {
  public loggedIn:boolean = false;
  @ViewChild("name")
  private name : ElementRef;
  @ViewChild("desc")
  private desc : ElementRef;
  @ViewChild("connection")
  private connection : ElementRef;
  private projectId : number;
  private baseUrl = environment.apiBaseUrl;
  private subscrip : Subscription;
  constructor(private router:Router, private service:SessionService) { }

  ngOnInit(): void {
    let urlSplit = this.router.url.split("/");
    if(urlSplit.length!=3) {
      console.log("HERE 1")
      this.goHome();
    }

    try{this.projectId = <number><unknown> urlSplit[2].valueOf();}
    catch(e){
      console.log("HERE 2")
      this.goHome();
    }

    this.service.checkSession();

    this.subscrip = this.service.myStatus$.subscribe(stat=>{
      if(stat!=null) {
        let project:Project;
        this.getProject(this.projectId)
        .then(resp=>{
          project=resp;
          if(project==null){
            console.log("HERE 3")
            this.goHome();
          }
          
          this.isAuthorized(stat,project)
          .then(authorized=>{
            if(authorized){
              this.loggedIn=true;
              this.main(project);
            }else{
              console.log("HERE 4")
              this.goHome();
            }
          })
        })
      }
      else {
        this.loggedIn=false;
        console.log("HERE 4")
        this.goHome();
      }
    })

  }

  ngOnDestroy(){
    if(this.subscrip!=null) {
      console.log("DESTROOOY")
      this.subscrip.unsubscribe();
    }
  }

  isAuthorized(memberId:string, project:Project):Promise<boolean>{
    let member:Member;
    console.log("is authorized!")
    return this.getMember(memberId).then(m=>{
      member=m
      for(let i in project.projectLeaders){
        if(memberId==project.projectLeaders[i]) return true;
      }
      return false;
    });
  }

  getMember(memberId:string):Promise<Member>{
    let url = this.baseUrl + "/members/" + memberId;
    return fetch(url,{credentials:'include'})
    .then(resp=>resp.json())
    .then(member=>member)
  }

  async goHome(){
    await this.router.navigate(['/']);//.then(()=>location.reload());
  }

  
  main(project:Project){
    if(!this.name && !this.desc){
      setTimeout(()=>{
        this.name.nativeElement.value = project.name;
        this.desc.nativeElement.value = project.description;
        let button = document.getElementsByName("submit")[0];
        button.className = "btn btn-primary";
      })
    }
  }

  getProject(id:number){
    let url = this.baseUrl + "/projects/" + id;
    return fetch(url,{credentials:'include'}).then(resp=>resp.json()).then(data=>data);
  }
  update(){
    console.log("UPDATE!")
  }

}
