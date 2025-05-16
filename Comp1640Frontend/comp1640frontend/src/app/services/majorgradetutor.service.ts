import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Majorgradetutor } from '../common/majorgradetutor';

@Injectable({
  providedIn: 'root'
})
export class MajorgradetutorService {

  private majorGradeTutorUrl = 'http://localhost:8080/api/majorgradeTutor';

  constructor(private HttpClient: HttpClient) { }

  saveMajorGradeTutor( majorGradeTutor:  Majorgradetutor[]): Observable<any> {
    const saveMajorgradeTutor = `${this.majorGradeTutorUrl}/save_majorgradeTutor`;
    return this.HttpClient.post(saveMajorgradeTutor,majorGradeTutor);
  }

  
  getAllMajorgradeTutorDtoByTutorId(tutorId: number): Observable<any> {
    return this.HttpClient.get<any>(`${this.majorGradeTutorUrl}/${tutorId}`);
  }
  
  deleteMajorGradeTutor(majorGradeTutorSet: Set<Majorgradetutor>): Observable<void> {
    const majorGradeTutorArray = Array.from(majorGradeTutorSet);
    console.log(majorGradeTutorArray)
    return this.HttpClient.post<void>(`${this.majorGradeTutorUrl}/delete_majorgradeTutor`,majorGradeTutorArray);
  }
}
