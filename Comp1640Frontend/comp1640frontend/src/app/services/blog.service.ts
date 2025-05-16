import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable } from 'rxjs';
import { Blog } from '../common/blog';


@Injectable({
  providedIn: 'root'
})
export class BlogService {

  private blogUrl = 'http://localhost:8080/api/blog'; // URL backend

  constructor(private HttpClient: HttpClient) { }


  getBlogList(): Observable<any> {
    return this.HttpClient.get<any>(this.blogUrl);
  }

  saveBlog(blogFormData: FormData): Observable<any> {
    const saveBlog = `${this.blogUrl}/save_blog`;
    return this.HttpClient.post(saveBlog, blogFormData, {responseType: 'text'});
  }
  
  downloadFile(blogId: number): Observable<Blob> {
    const url = `${this.blogUrl}/download/${blogId}`;
    return this.HttpClient.get(url, { responseType: 'blob' });
  }  

  deleteBlog(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.blogUrl}/delete/${id}`);
  }

  updateBlog(blogFormData: FormData, blogId: number):Observable<any>{
    return this.HttpClient.put(`${this.blogUrl}/update/${blogId}`, blogFormData, { responseType: 'text' });
  }

}
