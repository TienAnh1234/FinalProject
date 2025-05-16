import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MajorgradeService {



  private majorGraderl = 'http://localhost:8080/api/majorGrade';



  constructor(private HttpClient: HttpClient) { }

  getMajorGraderlList(): Observable<any> {
    return this.HttpClient.get<any>(this.majorGraderl);
  }

}
