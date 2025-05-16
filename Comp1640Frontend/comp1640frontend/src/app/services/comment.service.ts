import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Commentblog } from 'src/app/common/commentblog';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private commentUrl = 'http://localhost:8080/api/comment';


  constructor(private HttpClient: HttpClient) { }

  getCommentList(): Observable<any> {
    return this.HttpClient.get<any>(this.commentUrl);
  }


  saveComment(commentBlog: Commentblog): Observable<any> {
    console.log(commentBlog.userId)
    const saveMajor = `${this.commentUrl}/save_comment`;
    return this.HttpClient.post(saveMajor, commentBlog , { responseType: 'text' });
  }

  deleteComment(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.commentUrl}/delete/${id}`);
  }

  updateComment(comment: Commentblog, commentId: number):Observable<any>{
    console.log(commentId)
    console.log(comment)

    return this.HttpClient.put(`${this.commentUrl}/update/${commentId}`, comment, { responseType: 'text' });
  }


}
