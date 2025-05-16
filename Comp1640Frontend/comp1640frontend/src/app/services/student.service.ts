import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Student } from '../common/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private studentUrl = 'http://localhost:8080/api/student';


  constructor(private HttpClient: HttpClient) { }

    getStudentList(): Observable<any> {
      return this.HttpClient.get<any>(this.studentUrl);
    }

    updateStudent(studentFormData: FormData, studentId: number):Observable<any>{
      return this.HttpClient.put(`${this.studentUrl}/update/${studentId}`, studentFormData, { responseType: 'text' });
    }


  
}
