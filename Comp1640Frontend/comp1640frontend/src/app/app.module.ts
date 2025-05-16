import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { AdminModule } from './components/admin/admin.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { UserModule } from './components/user/user.module';
import { LoginComponent } from './components/login/login.component';
import { BlognologinComponent } from './components/blognologin/blognologin.component';
import { RegisterComponent } from './components/register/register.component';
import { ResetpasswordComponent } from './components/resetpassword/resetpassword.component';



const routes: Routes = [
{ path: 'login', component: LoginComponent },
{ path: 'admin', component: AdminComponent },
{ path: 'user', component: UserComponent },
{ path: 'blognologin', component: BlognologinComponent },
{ path: 'register', component: RegisterComponent },
{path: 'resetpassword', component: ResetpasswordComponent },


{ path: '', redirectTo: '/blognologin', pathMatch: 'full' },

];


@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    RegisterComponent,
    ResetpasswordComponent

  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    AdminModule,
    UserModule,
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  bootstrap: [AppComponent]
}
)
export class AppModule { }
