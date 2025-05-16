import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BlogComponent } from './blog/blog.component';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { FormsModule } from '@angular/forms'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from 'src/app/auth.interceptor';
import { ScheduleComponent } from './schedule/schedule.component';
import { MyblogComponent } from './myblog/myblog.component';
import { BlognologinComponent } from '../blognologin/blognologin.component';
import { SearchblogComponent } from './searchblog/searchblog.component';
import { SearchuserComponent } from './searchuser/searchuser.component';
import { ByprimeComponent } from './byprime/byprime.component';
import { PaymentreturnComponent } from './paymentreturn/paymentreturn.component';


const routes: Routes = [


  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { role: ['ADMIN','TUTOR','STUDENT'] }, 
    children :[      
      {path: 'blog', component: BlogComponent},
      {path: 'schedule', component: ScheduleComponent},
      {path: 'myblog', component: MyblogComponent},
      {path: 'searchblog', component: SearchblogComponent},
      {path: 'searchuser', component: SearchuserComponent},
      {path: 'byprime', component: ByprimeComponent},
      {path: 'vnpay-payment', component: PaymentreturnComponent },




      { path: '', redirectTo: '/user/blog', pathMatch: 'full' },
    ]
  },

];


@NgModule({
  declarations: [
    BlogComponent,
    ScheduleComponent,
    MyblogComponent,
    BlognologinComponent,
    SearchblogComponent,
    SearchuserComponent,
    ByprimeComponent,
    PaymentreturnComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule 
  ],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }  
    ]
  
})
export class UserModule { }
