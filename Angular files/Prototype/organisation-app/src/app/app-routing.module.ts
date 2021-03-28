import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { SignupComponent } from './signup/signup.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { ShowProjectsComponent } from './show-projects/show-projects.component';
import { ShowMembersComponent } from './show-members/show-members.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: 'show-members', component: ShowMembersComponent},
  {path: 'show-projects', component: ShowProjectsComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'signUp', component: SignupComponent},
  {path: 'new-project', component: NewProjectComponent},
  {path: 'edit-profile', component: EditProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
