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
  
  private project : Project;
  private projectId : number;
  public memberNames : string[] = [];
  private memberIds : string[] = [];

  private baseUrl = environment.apiBaseUrl;
  private subscrip : Subscription;
  constructor(private router:Router, private service:SessionService) { }

  ngOnInit(): void {
    let urlSplit = this.router.url.split("/");
    if(urlSplit.length!=3) {
      this.goHome();
    }

    try{this.projectId = <number><unknown> urlSplit[2].valueOf();}
    catch(e){
      this.goHome();
    }

    this.service.checkSession();

    this.subscrip = this.service.myStatus$.subscribe(stat=>{
      if(stat!=null) {
        this.getProject(this.projectId)
        .then(resp=>{
          this.project=resp;
          if(this.project==null){
            this.goHome();
          }
          
          this.isAuthorized(stat)
          .then(authorized=>{
            if(authorized){
              this.loggedIn=true;
              this.main();
            }else{
              this.goHome();
            }
          })
        })
      }
      else {
        this.loggedIn=false;
        this.goHome();
      }
    })

  }

  ngOnDestroy(){
    if(this.subscrip!=null) {
      this.subscrip.unsubscribe();
    }
  }

  isAuthorized(memberId:string):Promise<boolean>{
    let member:Member;
    return this.getMember(memberId).then(m=>{
      member=m;
      for(let i in this.project.projectLeaders){
        if(memberId==this.project.projectLeaders[i]) return true;
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

  
  async main(){
    this.getMemberNames();
    if(!this.name && !this.desc){
      setTimeout(()=>{
        this.name.nativeElement.value = this.project.name;
        this.desc.nativeElement.value = this.project.description;
        let button = document.getElementsByName("submit")[0];
        button.className = "btn btn-primary";
      })
    }
  }

  async getMemberNames(){
    for(let i in this.project.projectLeaders){
      let member = await this.getMember(<string>this.project.projectLeaders[i])
      this.memberNames.push(member.firstName+" "+member.lastName);
      this.memberIds.push(<string>this.project.projectLeaders[i]);
    }
    for(let i in this.project.projectMembers){
      let member = await this.getMember(<string>this.project.projectMembers[i])
      this.memberNames.push(member.firstName+" "+member.lastName);
      this.memberIds.push(<string>this.project.projectMembers[i]);
    }
  }

  getProject(id:number){
    let url = this.baseUrl + "/projects/" + id;
    return fetch(url,{credentials:'include'}).then(resp=>resp.json()).then(data=>data);
  }
  update(){
    let body = {
      name:this.name.nativeElement.value,
      description:this.desc.nativeElement.value
    }
    let headerContent = {
      "Content-Type":'application/json'
    }
    let url = this.baseUrl + "/projects/" + this.projectId;

    fetch(url,{credentials:'include',headers:headerContent, body:JSON.stringify(body), method:'PUT'})
    .then(resp=>resp.json())
    .then(data=>{
      if(data=="IM_USED"){
        this.name.nativeElement.setAttribute("class", "form-control is-invalid");
      }
      else if(data=="CREATED"){
        this.name.nativeElement.setAttribute("class", "form-control is-valid");
        this.desc.nativeElement.setAttribute("class", "form-control is-valid");
      }
    });
  }

  addTask(){
    console.log("ADD TASK!");
    let taskName = <HTMLInputElement> document.getElementById("task-name");
    let taskDesc = <HTMLInputElement> document.querySelector("#task-desc");
    let responsible:string[] = [];
    console.log("task name: ",taskName.value);
    console.log("task desc: ",taskDesc.value);

    for(let i in this.memberNames){
      let checkBox = <HTMLInputElement>document.querySelector("#check-"+i);
      if(checkBox.checked){
        responsible.push(this.memberIds[i]);
      }
    }
    let headerUid = "";
    for(let i in responsible){
      headerUid += responsible[i];
      if(i != (responsible.length-1).toString()) headerUid+="&";
    }
    console.log("do the job: ", headerUid);

    let url = this.baseUrl+"/tasks";
    let body = {
      name: taskName.value,
      description: taskDesc.value,
      status: "on progress"
    }

    //
    fetch(url,{
      method:'post',
      credentials:'include',
      headers:{'Content-Type': 'application/json', 'PID':this.projectId.toString(), 'UID':headerUid},
      body:JSON.stringify(body)
    })
  }
}
