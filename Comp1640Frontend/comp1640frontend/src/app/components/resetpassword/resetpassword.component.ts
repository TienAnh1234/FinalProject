import { HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Resetpassword } from 'src/app/common/resetpassword';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {


  @ViewChild('myModalEnterOtp') modalEnterOtp: ElementRef | undefined;



  timeLeft: number = 120;
  otp: string = '';
  timer: any;



  show() {
    this.otp = '';
    this.timeLeft = 120;  
    this.startCountdown();
  }

  startCountdown() {
    this.timer = setInterval(() => {
      this.timeLeft--;
      if (this.timeLeft <= 0) {
        clearInterval(this.timer);
        this.closeModalEnterOtp();
      }
    }, 1000);
  }


  closeModalEnterOtp(){
    if (this.modalEnterOtp) {
      this.modalEnterOtp.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
    clearInterval(this.timer);

  }

  openModalEnterOtp() {
    this.show();
      const modalElement = document.getElementById('myModalEnterOtp');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  resetPasswordFormGroup!: FormGroup;
  resetpassword!: Resetpassword

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router 
  ) { }

  ngOnInit(): void {
    this.createForm()
  }



    createForm() {
      this.resetPasswordFormGroup = this.fb.group({
        email: ['', Validators.required],
        newPassword: ['', Validators.required],
        newPasswordAgain: ['', Validators.required],
        username: ['',Validators.required ]
      });
    }

    onSubmit() {
      if (this.resetPasswordFormGroup.valid) {
        if(this.resetPasswordFormGroup.value.newPassword === this.resetPasswordFormGroup.value.newPasswordAgain ){

          const resetpassword = {
            email: this.resetPasswordFormGroup.value.email,
            newPassword: this.resetPasswordFormGroup.value.newPassword,
            username: this.resetPasswordFormGroup.value.username
          }
          console.log(resetpassword)
          
          this.authService.sendyOtp(resetpassword.email,resetpassword.newPassword,resetpassword.username ).subscribe(response => {
              this.openModalEnterOtp()
            })


        }else{
          alert('Check your password!');
        } 
      }else{
        alert('Please enter enough infomation!');
      }
    }

    submitOtp(){
      this.authService.verifyOtp(this.resetPasswordFormGroup.value.email, this.otp ).subscribe(
        response => {
          if(response === 'Verified'){

          const resetpassword = {
            email: this.resetPasswordFormGroup.value.email,
            newPassword: this.resetPasswordFormGroup.value.newPassword,
            username: this.resetPasswordFormGroup.value.username
          }

            this.authService.resetpassword(resetpassword.email,resetpassword.newPassword,resetpassword.username ).subscribe(data=>{
            this.router.navigate(['/login']); // Chuyển về trang đăng nhập
          })
          }else{
            alert('Wrong OTP')
          }

        }
      )
    }


    resendOtp(){

    }

}
