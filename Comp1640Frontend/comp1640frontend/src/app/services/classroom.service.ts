import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { from, Observable, Subject } from 'rxjs';
import { Classroom } from '../common/classroom';
import { StudentService } from './student.service';
import { TutorService } from './tutor.service';
import { Student } from '../common/student';
import { Tutor } from '../common/tutor';
import { ClassroomMain } from '../common/classroom-main';
import { Friendrequest } from '../common/friendrequest';

@Injectable({
  providedIn: 'root'
})
export class ClassroomService {

  private classrooomUrl = 'http://localhost:8080/api/classroom';

  students: Student[] =[];
  tutors: Tutor[] =[];
  classroomMainsList: ClassroomMain[] = [];


  classroomsList: Classroom[]=[];
  constructor(private HttpClient: HttpClient
  ) { }

  

  getClassroomList(): Observable<any> {
    return this.HttpClient.get<any>(this.classrooomUrl);
  }

  saveClassroom(classroom: Classroom): Observable<any> {
    const saveClassroom = `${this.classrooomUrl}/save_classroom`;
    return this.HttpClient.post(saveClassroom, classroom, { responseType: 'text' });
  }

  updateClassroom(classroom: Classroom):Observable<any>{
    return this.HttpClient.put(`${this.classrooomUrl}/update`, classroom, { responseType: 'text' });
  }

  deleteClassOfTutor(tutorId: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.classrooomUrl}/delete/${tutorId}`);
  }


  deleteClassByStudentIdAndTutorId(tutorId: number,studentId: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.classrooomUrl}/delete/${tutorId}/${studentId}`);
  }


  saveClassroomMain(friendrequest: Friendrequest): Observable<any> {
    const saveClassroom = `${this.classrooomUrl}/save_classroomMain`;
    return this.HttpClient.post(saveClassroom, friendrequest, { responseType: 'text' });
  }

  deleteFriendRequest(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.classrooomUrl}/deleteFriendRequest/${id}`);
  }

  getAllFriendrequest(): Observable<any> {
    return this.HttpClient.get<any>(`${this.classrooomUrl}/getAllFriendRequest`);
  }


}
