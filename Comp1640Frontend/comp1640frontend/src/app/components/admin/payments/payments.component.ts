import { Component, OnInit } from '@angular/core';
import { Individualpayment } from 'src/app/common/individualpayment';
import { Tutor } from 'src/app/common/tutor';
import { PaymentService } from 'src/app/services/payment.service';
import { TutorService } from 'src/app/services/tutor.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit {

  payments: Individualpayment[] = []
  tutors: Tutor[] = []
  constructor(private paymentService: PaymentService,
              private tutorService: TutorService
  ) { }

  ngOnInit(): void {
    this.listPayments()
    this.listTutor()
  }

  listTutor() {
    this.tutorService.getTutorList().subscribe(
      data =>{
        this.tutors = data;
        console.log(this.tutors);
      }
    )
  }
  
  listPayments() {
    this.paymentService.getPaymentList().subscribe(
      data =>{
        this.payments = data;
      }
    )
  }

  findTutorById(tutorId: number):Tutor{
    return this.tutors.filter(tutor => tutor.id === tutorId)[0]
  }


    deletePayment(paymentId:number) {
      if (confirm('Do you want to delete this account?')) {
        this.paymentService.deletePayment(paymentId).subscribe(
          {
            next: () => {
              console.log('Delete Successfully');
              this.listPayments();
            },
            error: (error) => {
              console.error('Delete failedly:', error);
            }
          }
        )
      }
    }

}
