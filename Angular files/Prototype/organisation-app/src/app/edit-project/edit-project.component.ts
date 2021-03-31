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
      this.goHome();
    }

    try{this.projectId = <number><unknown> urlSplit[2].valueOf();}
    catch(e){
      this.goHome();
    }

    this.service.checkSession();

    this.subscrip = this.service.myStatus$.subscribe(stat=>{
      if(stat!=null) {
        this.loggedIn=true;
        this.main();
      }
      else {
        this.loggedIn=false;
        this.goHome();
      }
    })

  }

  ngOnDestroy(){
    this.subscrip.unsubscribe();
  }

  goHome(){
    this.router.navigate(['/']);
    location.reload();
  }

  
  main(){
    let project:Project;
    this.getProject(this.projectId)
    .then(resp=>{
      project=resp;

      if(resp!=null){
        this.name.nativeElement.value = project.name;
        this.desc.nativeElement.value = project.description;
        let button = document.getElementsByName("submit")[0];
        button.className = "btn btn-primary";
      }
    });
  }

  getProject(id:number){
    let url = this.baseUrl + "/projects/" + id;
    return fetch(url,{credentials:'include'}).then(resp=>resp.json()).then(data=>data);
  }
  update(){
    console.log("UPDATE!")
  }

}
