import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Resetpassword } from '../common/resetpassword';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth'; // URL backend


  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password },{ responseType: 'text' });
  }

  register(username: string, password: string, email: string, name: string, birthday: string, role: string,district:string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { username, password, email, name, birthday, role, district },{ responseType: 'text' });
  }
  
  resetpassword(email: string, newPassword: string, username: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/resetpassword`, { username, newPassword, email});
  }


  sendyOtp(email: string, newPassword: string, username: string,): Observable<any>{
    return this.http.post(`${this.apiUrl}/sendOtp`, { username, newPassword, email})
  }

  verifyOtp( email: string, otp: string,): Observable<any>{
    return this.http.post(`${this.apiUrl}/verify-otp`, {email,otp},{ responseType: 'text' })
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/blognologin']); // Chuyển về trang đăng nhập
  }


}
