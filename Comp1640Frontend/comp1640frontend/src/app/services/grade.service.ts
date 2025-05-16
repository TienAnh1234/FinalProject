import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GradeService {

  private gradeUrl = 'http://localhost:8080/api/grade';



  constructor(private HttpClient: HttpClient) { }

  getGradeList(): Observable<any> {
    return this.HttpClient.get<any>(this.gradeUrl);
  }

  
}
