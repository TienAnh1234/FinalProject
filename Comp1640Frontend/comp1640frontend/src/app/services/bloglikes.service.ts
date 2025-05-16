import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BloglikesService {

  private baseUrl = 'http://localhost:8080/api/blogLikes';

  constructor(private http: HttpClient) { }

  toggleLike(userId: number, blogId: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/${userId}/${blogId}`, {}, { responseType: 'text' });
  }

  getLikeCount(blogId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count/${blogId}`);
  }
  
}
