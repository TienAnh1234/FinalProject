import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class TutorService {

  private tutorUrl = 'http://localhost:8080/api/tutor';


  constructor(private HttpClient: HttpClient) { }

    getTutorList(): Observable<any> {
      return this.HttpClient.get<any>(this.tutorUrl);
    }

    updateTutor(tutorFormData: FormData, tutorId: number):Observable<any>{
      return this.HttpClient.put(`${this.tutorUrl}/update/${tutorId}`, tutorFormData, { responseType: 'text' });
    }





}
