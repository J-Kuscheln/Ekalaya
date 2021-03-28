import { SessionService } from './service/session.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShowMembersComponent } from './show-members/show-members.component';
import { ShowProjectsComponent } from './show-projects/show-projects.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { SignupComponent } from './signup/signup.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    ShowMembersComponent,
    ShowProjectsComponent,
    LoginPageComponent,
    SignupComponent,
    NewProjectComponent,
    EditProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [SessionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
