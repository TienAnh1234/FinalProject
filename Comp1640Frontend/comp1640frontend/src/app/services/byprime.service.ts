import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Payment } from '../common/payment';

@Injectable({
  providedIn: 'root'
})
export class ByprimeService {


  private baseUrl = 'http://localhost:8080/api/vnpay';


  constructor(private http: HttpClient) { }

    createPayment(payment: Payment): Observable<any> {
      const saveBlog = `${this.baseUrl}/create-payment`;
      return this.http.post(saveBlog, payment);
    }

}
