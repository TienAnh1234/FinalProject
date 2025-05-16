import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../common/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/api/user';


  constructor(private HttpClient: HttpClient) { }


  saveUser(definedUserFormData: FormData): Observable<any> {
    const saveMajor = `${this.userUrl}/save_user`;
    return this.HttpClient.post(saveMajor, definedUserFormData);
  }

  getUserList(): Observable<any> {
    return this.HttpClient.get<any>(this.userUrl);
  }

  updateUser(user: User, userId: number):Observable<any>{
    return this.HttpClient.put(`${this.userUrl}/update/${userId}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.userUrl}/delete/${id}`);
  }

  findTutor(searchTutor: any): Observable<any> {
    const findTutor = `${this.userUrl}/findTutor`;
    return this.HttpClient.post(findTutor, searchTutor);
  }

  findStudent(searchStudent: any): Observable<any> {
    const findStudent = `${this.userUrl}/findStudent`;
    return this.HttpClient.post(findStudent, searchStudent);
  }

}
