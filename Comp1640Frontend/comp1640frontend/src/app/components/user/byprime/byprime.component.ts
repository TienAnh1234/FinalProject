import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Payment } from 'src/app/common/payment';
import { Tutor } from 'src/app/common/tutor';
import { AuthService } from 'src/app/services/auth.service';
import { ByprimeService } from 'src/app/services/byprime.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { TutorService } from 'src/app/services/tutor.service';

@Component({
  selector: 'app-byprime',
  templateUrl: './byprime.component.html',
  styleUrls: ['./byprime.component.css']
})
export class ByprimeComponent implements OnInit {

  payment!: Payment
  username!: string
  token = this.authService.getToken()!;
  tutors: Tutor[] = [];

  status!: string

  constructor(private http: HttpClient,
              private authService: AuthService,
              private byprime: ByprimeService,
              private jwtHelperService: JwtHelperService,
              private tutorService: TutorService
  ) {

   }

  ngOnInit(): void {
    this.username = this.jwtHelperService.getUserFromToken(this.token).username
    this.loadAllData()
  }

  loadAllData() {
    forkJoin({
      tutors: this.tutorService.getTutorList()

    }).subscribe(({tutors}) => {
      this.tutors = tutors
      this.setUserHold();
    });
    
  }


  setUserHold(){
    const userTutorOrStudent = this.tutors.filter(tutor => tutor.username === this.username)[0]
    this.status = userTutorOrStudent.status!
    console.log(userTutorOrStudent.status!)
  }

  listTutor() {
    this.tutorService.getTutorList().subscribe(
      data =>{
        this.tutors = data;
        console.log(this.tutors);
      }
    )
  }

  byPrimeOne(){

    if(this.status === 'PRIME'){
      alert('You bought prime, please wait until your prime expire')
      return;
    }

    const payment = new Payment(99000,'1Month:' + this.username )


    this.byprime.createPayment(payment).subscribe({
      next: res => {
        window.location.href = res.paymentUrl;
      },
      error: err => {
        console.log(err)
        alert('Tạo thanh toán thất bại');
      }
    })
  }

  byPrimeTwo(){

    if(this.status === 'PRIME'){
      alert('You bought prime, please wait until your prime expire')
      return;
    }

    const payment = new Payment(249000,'3Month:' + this.username )

    this.byprime.createPayment(payment).subscribe({
      next: res => {
        window.location.href = res.paymentUrl;
      },
      error: err => {
        console.log(err)
        alert('Tạo thanh toán thất bại');
      }
    })
  }

  byPrimeThree(){

    if(this.status === 'PRIME'){
      alert('You bought prime, please wait until your prime expire')
      return;
    }

    const payment = new Payment(449000,'6Month:' + this.username )

    this.byprime.createPayment(payment).subscribe({
      next: res => {
        window.location.href = res.paymentUrl;
      },
      error: err => {
        console.log(err)
        alert('Tạo thanh toán thất bại');
      }
    })
  }



}
