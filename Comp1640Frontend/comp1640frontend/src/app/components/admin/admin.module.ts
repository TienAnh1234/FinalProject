import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StudentsComponent } from './students/students.component';
import { AdminComponent } from './admin.component';
import { TutorsComponent } from './tutors/tutors.component';
import { MajorsComponent } from './majors/majors.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { UserAccountComponent } from './user-account/user-account.component';
import { ClassroomComponent } from './classroom/classroom.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { AuthInterceptor } from 'src/app/auth.interceptor';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgChartsModule } from 'ng2-charts';
import { ApproveblogComponent } from './approveblog/approveblog.component';
import { PaymentsComponent } from './payments/payments.component';

const routes: Routes = [

  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { role: ['ADMIN'] }, 
    children :[
      {path: 'students', component: StudentsComponent},
      {path: 'tutors', component: TutorsComponent},
      {path: 'majors', component: MajorsComponent},
      {path: 'usersAccount', component: UserAccountComponent},
      {path: 'classroom', component: ClassroomComponent},
      {path: 'dashboard', component: DashboardComponent},
      {path: 'approveBlog', component: ApproveblogComponent},
      {path: 'payment', component: PaymentsComponent},


      
      { path: '', redirectTo: '/admin/students', pathMatch: 'full' },

    ]
  },
// { path: '', redirectTo: 'admin/students', pathMatch: 'full' },
// { path: '**', redirectTo: 'admin/students', pathMatch: 'full' }


];



@NgModule({
  declarations: [
    UserAccountComponent,
    StudentsComponent,
    TutorsComponent,
    MajorsComponent,  
    ClassroomComponent, 
    DashboardComponent, 
    ApproveblogComponent, PaymentsComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
    FormsModule,
    NgChartsModule 
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }  
  ]
})
export class AdminModule { }
