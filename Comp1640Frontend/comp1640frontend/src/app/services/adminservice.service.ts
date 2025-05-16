import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminserviceService {

  private adminUrl = 'http://localhost:8080/api/admin';


  constructor(private HttpClient: HttpClient) { }

    getAdminList(): Observable<any> {
      return this.HttpClient.get<any>(this.adminUrl);
    }

    updateAdmin(adminFormData: FormData, adminId: number):Observable<any>{
      return this.HttpClient.put(`${this.adminUrl}/update/${adminId}`, adminFormData, { responseType: 'text' });
    }
}
  