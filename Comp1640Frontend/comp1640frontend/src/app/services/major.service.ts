import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Major } from '../common/major';

@Injectable({
  providedIn: 'root'
})
export class MajorService {


  private majorUrl = 'http://localhost:8080/api/major';


  constructor(private HttpClient: HttpClient) { }


  saveMajor(major: Major): Observable<any> {
    const saveMajor = `${this.majorUrl}/save_major`;
    return this.HttpClient.post(saveMajor, major);
  }

  getMajorList(): Observable<any> {
    return this.HttpClient.get<any>(this.majorUrl);
  }


  deleteMajor(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.majorUrl}/delete/${id}`);
  }

  updateMajor(major: Major, majorId: number):Observable<any>{
    return this.HttpClient.put(`${this.majorUrl}/update/${majorId}`, major);
  }



}
