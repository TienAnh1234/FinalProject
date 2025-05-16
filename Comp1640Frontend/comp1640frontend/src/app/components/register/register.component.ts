import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { District } from 'src/app/common/district';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { DistrictService } from 'src/app/services/district.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  @ViewChild('myModalEnterOtp') modalEnterOtp: ElementRef | undefined;
  
  otp: string = '';
  timeLeft: number = 120;
  timer: any;
  users: User[] =[];

  startCountdown() {
    this.timer = setInterval(() => {
      this.timeLeft--;
      if (this.timeLeft <= 0) {
        clearInterval(this.timer);
        this.closeModalEnterOtp();
      }
    }, 1000);
  }

  submitOtp(){
    this.authService.verifyOtp(this.registerForm.value.email, this.otp ).subscribe(
      response => {
        console.log(response);
        if(response === 'Verified'){
          const definedUserFormData = new FormData();
          definedUserFormData.append('username', this.registerForm.value.username);
          definedUserFormData.append('password', this.registerForm.value.password);
          definedUserFormData.append('email', this.registerForm.value.email);
          definedUserFormData.append('role', this.registerForm.value.role);
          definedUserFormData.append('name', this.registerForm.value.name);
          definedUserFormData.append('birthday', this.registerForm.value.birthday);
          definedUserFormData.append('district', this.registerForm.value.district);
          this.userService.saveUser(definedUserFormData).subscribe(data=>{
            this.router.navigate(['/login']); // Chuyển về trang đăng nhập
          }) 
          
          console.log(this.registerForm.value.name + ' hoinofhjcij ')
            
          this.closeModalEnterOtp()
          

        }else{
          alert('Wrong OTP')
        }
        
      });
  }

  resendOtp(){
    this.show();
    clearInterval(this.timer);

    const { username, password,email,name,birthday,role,district } = this.registerForm.value;
    this.authService.register(username, password, email, name, birthday, role, district ).subscribe(
      response => {
        console.log(response);
      });
  }

  show() {
    this.otp = '';
    this.timeLeft = 120;  
    this.startCountdown();
  }

  districts: District[] =[];
  registerForm!: FormGroup;

  constructor(private fb: FormBuilder,
              private districtService: DistrictService,
              private authService: AuthService,
              private userService: UserService,
              private router: Router ) { }

  ngOnInit(): void {
    this.listDistrict();
    this.listUser();
    this.createForm();
  }

  listDistrict(){
    this.districtService.getDistrictList().subscribe(
      data =>{
        this.districts = data;
      }
    )
  }

  listUser(){
    this.userService.getUserList().subscribe(
      data =>{
        this.users = data;
      }
    )
  }

  createForm() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      name: ['', Validators.required],
      birthday: ['', Validators.required],
      role: ['', Validators.required],
      district: ['', Validators.required]

    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      if(this.registerForm.value.password === this.registerForm.value.confirmPassword ){

        if(this.users.some(user => user.username === this.registerForm.value.username)){
          alert(this.registerForm.value.username + ' existed, Please choose another username!');
        }else{
          const { username, password,email,name,birthday,role,district } = this.registerForm.value;
          this.authService.register(username, password, email, name, birthday, role, district ).subscribe(
            response => {
              console.log(response);
              this.openModalEnterOtp()
            });
        }
      }else{
        alert('Check your password!');
      } 
    }else{
      alert('Please enter enough infomation!');
    }
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

}
