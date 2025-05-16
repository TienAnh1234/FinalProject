import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Schedule } from '../common/schedule';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private apiUrl = 'http://localhost:8080/api/schedule';

  constructor(private http: HttpClient
  ) { }

  bookSchedule(schedule: Schedule,checkboxValue:number): Observable<Schedule> {

    return this.http.post<Schedule>(`${this.apiUrl}/book_schedule?weakNumber=${checkboxValue}`, schedule);
  }

  formatLocalDateToISOString(date: Date): string {
    return date.getFullYear() + '-' +
           String(date.getMonth() + 1).padStart(2, '0') + '-' +
           String(date.getDate()).padStart(2, '0') + 'T' +
           String(date.getHours()).padStart(2, '0') + ':' +
           String(date.getMinutes()).padStart(2, '0') + ':' +
           String(date.getSeconds()).padStart(2, '0');
  }

  getScheduleList(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}`);
  }

  getScheduleListAll(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getStudentScheduleList(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/student/${userId}`);
  }

  updateSchedule(schedule: Schedule, scheduleId: number):Observable<any>{
    return this.http.put(`${this.apiUrl}/update/${scheduleId}`, schedule);
  }
  
  deleteSchedule(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  deleteByTutorIdAndStudentId(tutorId: number, studentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${tutorId}/${studentId}`);
  }

}
