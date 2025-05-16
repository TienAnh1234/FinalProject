import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Individualpayment } from '../common/individualpayment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private paymentUrl = 'http://localhost:8080/api/payment';


  constructor(private HttpClient: HttpClient) { }

  getPaymentList(): Observable<Individualpayment[]> {
    return this.HttpClient.get<Individualpayment[]>(this.paymentUrl);
  }

  deletePayment(id: number): Observable<void> {
    return this.HttpClient.delete<void>(`${this.paymentUrl}/delete/${id}`);
  }

}
