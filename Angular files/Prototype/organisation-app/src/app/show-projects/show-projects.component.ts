import { environment } from './../../environments/environment';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { Project } from './../project/Project';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-show-projects',
  templateUrl: './show-projects.component.html',
  styleUrls: ['./show-projects.component.scss']
})
export class ShowProjectsComponent implements OnInit {
  projects: Project[]
  myProjects: Project[]
  projectsContainer: Array<Project[]>
  loggedIn:boolean = false;
  private userfirstName: string
  private userlastName: string
  private id: string
  private baseUrl = environment.apiBaseUrl
  constructor(private session:SessionService, private router:Router) { 
    this.projects = [];
    this.myProjects = [];
    this.projectsContainer = [];
    this.id = null;
    this.userfirstName = null;
  }

  ngOnInit(): void {
    this.session.myUserFirstName$.subscribe((sessionName)=>this.userfirstName = sessionName)
    this.session.myUserLastName$.subscribe((sessionName)=>this.userlastName = sessionName)

    this.session.myStatus$.subscribe(sessionStat=>{
      if(sessionStat!=null){
        console.log("logged in");
        this.loggedIn = true;
        this.id = sessionStat;
      }else {
        console.log("logged out");
        this.loggedIn=false;
      }
    });
    
    this.session.checkSession();
    
    this.getProjects()
    .then(data=>{
      data.forEach(project => {
        project.description = project.description.replaceAll('\n', "<br>");
        if(project.projectLeaders.length>0){
          let decided = false
          for(let index in project.projectLeaders){
            if(<string>project.projectLeaders[index] == this.id){
              this.myProjects.push(project);
              decided = true
              break;
            }
          }
          if(!decided){
            for(let index in project.projectMembers){
              if(<string>project.projectMembers[index] == this.id){
                this.myProjects.push(project);
                decided = true
                break;
              }
            }
            if(!decided)this.projects.push(project);
          }
          
        }else this.projects.push(project);
        
      });
      this.projects.forEach(project => {
        for(let index in project.projectLeaders){
          this.updateMemberName(project.projectLeaders[index]).then(str=>project.projectLeaders[index] = str);
        }
        for(let member in project.projectMembers){
          this.updateMemberName(project.projectMembers[member]).then(t=>project.projectMembers[member] = t);
        }
      })

      this.myProjects.forEach(project => {
        for(let index in project.projectLeaders){
          this.updateMemberName(project.projectLeaders[index]).then(str=>project.projectLeaders[index] = str);
        }
        for(let member in project.projectMembers){
          this.updateMemberName(project.projectMembers[member]).then(t=>project.projectMembers[member] = t);
        }
      })
      
      this.projectsContainer.push(this.myProjects)
      this.projectsContainer.push(this.projects)
    });
    
    
  }

  getProjects(){
    return fetch(this.baseUrl+"/projects",{credentials:'include'}).then(Response=>Response.json())
    .then(data=>data)
  }

  updateMemberName(id:String){
    let url = this.baseUrl+"/members/" + id;
    return fetch(url,{credentials:'include'})
    .then(response=>response.json())
    .then(member=>{
      let fullName: string = <string>member.firstName + " " + <string>member.lastName
      return fullName;
    })
  }

  edit(id:number){
    document.getElementById("edit_"+id).setAttribute("style", "display:none")
    document.getElementById("remove_"+id).setAttribute("style", "display:inline")
    document.getElementById("editDetails_"+id).setAttribute("style", "display:inline")
    document.getElementById("done_"+id).setAttribute("style", "display:inline")
  }

  removeProject(id:number){
    let url:string = this.baseUrl+"/projects/" + id
    fetch(url,{method:'DELETE', credentials:'include'})
    .then(()=>{location.reload();})
    .catch(()=>console.log("fail to delete"))
  }

  removeAsMember(id:number){
    let requestBody = `{"memberId":"`+ this.id +`",
    "projectId":`+ id +`,
    "toDo":"removeAsMember"
    }`

    let url:string = this.baseUrl+"/relate";

    fetch(url,
      { method:'POST',
        credentials:'include',
        headers:{'Content-Type': 'application/json'},
        body:requestBody
      })
    .then(()=>location.reload())
    .catch(()=>console.log("failed to remove as member"))

  }

  removeAsLeader(id:number){
    let requestBody = `{"memberId":"`+ this.id +`",
    "projectId":`+ id +`,
    "toDo":"removeAsLeader"
    }`

    let url:string = this.baseUrl+"/relate";

    fetch(url,
      { method:'POST',
        credentials:'include',
        headers:{'Content-Type': 'application/json'},
        body:requestBody
      })
    .then(()=>location.reload())
    .catch(()=>console.log("failed to remove as leader"))

  }

  cancel(id:number){
    document.getElementById("edit_"+id).setAttribute("style", "display:inline")
    document.getElementById("remove_"+id).setAttribute("style", "display:none")
    document.getElementById("editDetails_"+id).setAttribute("style", "display:none")
    document.getElementById("done_"+id).setAttribute("style", "display:none")
  }

  details(id:number){
    console.log("details id: ",id)
  }

  modifyModal(id:number, name:String){
    let modal = document.getElementById("removeModal");
    let body = modal.querySelector(".modal-body");
    let removeBtn = modal.querySelector("#removeBtn");
    let project:Project = null;

    body.textContent = "Are you sure want to remove the project \"" + name +  "\"?";
    removeBtn.addEventListener('click',()=>{
      this.myProjects.forEach((myProject)=>{
        if (myProject.name == name) project = myProject;
      })
      project.projectLeaders.forEach((leader)=>leader== this.userfirstName + " " + this.userlastName ? this.removeAsLeader(id) : this.removeAsMember(id))
      if(project.projectLeaders.length==0) this.removeProject(id);
    })
  }

  join(id:number){
    let requestBody = `{"memberId":"`+ this.id +`",
    "projectId":`+ id +`,
    "toDo":"asMember"
    }`

    let url:string = this.baseUrl+"/relate";

    fetch(url,
      { method:'POST', 
        credentials:'include',
        headers:{'Content-Type': 'application/json'},
        body:requestBody
      })
    .then(()=>location.reload())
    .catch(()=>console.log("failed to relate as member"))
  }
}
