import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PopularSubject } from '../common/popularsubject';
import { MonthlyTotalAmount } from '../common/monthlyTotalAmount';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private baseUrl = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) { }


  getStudentsPerMajor(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students-per-major`);
  }

  getTutorsPerMajor(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tutors-per-major`);
  }

  getTotalAccounts(): Observable<{ student: number, tutor: number }> {
    return this.http.get<{ student: number, tutor: number }>(`${this.baseUrl}/new-accounts-total`);
  }
  
  getBlogsByMonth(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/blogs-by-month`);
  }  
  
  getPopularSubjectProjection(): Observable<PopularSubject[]> {
    return this.http.get<PopularSubject[]>(`${this.baseUrl}/most-popular-subject`);
  }  

  getMonthlyTotalAmount(): Observable<MonthlyTotalAmount[]> {
    return this.http.get<MonthlyTotalAmount[]>(`${this.baseUrl}/monthly-totalAmount`);
  } 
  
}
