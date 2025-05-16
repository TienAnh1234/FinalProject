import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Major } from 'src/app/common/major';
import { User } from 'src/app/common/user';
import { BlogService } from 'src/app/services/blog.service';
import { MajorService } from 'src/app/services/major.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {


@ViewChild('myModal') model: ElementRef | undefined;
@ViewChild('myModalUpdateUser') modelUpdateUser: ElementRef | undefined;


  userForm!: FormGroup;
  userFormUpdate!: FormGroup;

  users: User[] =[];
  majors: Major[] =[];
  
  userUpdateId!: number;
  showNotification = false;
  selectedFile!: File;
  showMajor: Boolean =false;

  usernameSearch: string = '';

  constructor(
              private formBuilder: FormBuilder,
              private majorService: MajorService,
              private userService: UserService,
              private router: Router
            ) { }

  ngOnInit(): void {
          this.userFormUpdate = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            password: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.minLength(3)]],
            role: [],
          });

          this.userForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            password: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.minLength(3)]],
            role: ['', [Validators.required, Validators.minLength(3)]],
            name: ['', [Validators.required, Validators.minLength(3)]],
            birthday: ['', [Validators.required, Validators.minLength(3)]],
            imageFile: ['', [Validators.required]],
            major: [''],
          });
          this.listUser();
          this.listMajor();

          this.userForm.get('role')?.valueChanges.subscribe((selectedRole) => {
            // Kiểm tra nếu role được chọn là "STUDENT" hoặc "TUTOR" thì hiển thị dropdown Major
            if (selectedRole === 'STUDENT' || selectedRole === 'TUTOR') {
              this.showMajor = true;
            } else {
              // Nếu chọn "ADMIN", ẩn dropdown Major và xóa giá trị đã chọn trước đó
              this.showMajor = false;
              this.userForm.patchValue({  
                major: ''
              });
            }
          });

  }


  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }


  
openModal() {
  const modalElement = document.getElementById('myModal');
  if (modalElement) {
    modalElement.style.display = 'block';
  }
}

openModalUpdate() {
  const modalElement = document.getElementById('myModalUpdateUser');
  if (modalElement) {
    modalElement.style.display = 'block';
  }
}

closeModal() {
  if (this.model) {
    this.model.nativeElement.style.display = 'none';
  }
}

closeModalUpdate() {
  if (this.modelUpdateUser) {
    this.modelUpdateUser.nativeElement.style.display = 'none';
  }
}


onSubmitUpdate() {
  if(this.userFormUpdate.valid == false){
    this.showNotification = true;
    setTimeout(() => {
      this.showNotification = false;
    }, 3000);
    console.log('Form không hợp lệ!', this.userForm.controls);
  }else{
    const newUser: User = {
      username: this.userFormUpdate.value.username,
      password: this.userFormUpdate.value.password,
      email: this.userFormUpdate.value.email,
      role: this.userFormUpdate.value.role
    };
    console.log(newUser);
    this.userService.updateUser(newUser, this.userUpdateId).subscribe(
      data =>{
        console.log(data);  
          this.listUser();
      }
    )
    this.closeModalUpdate();  

  }
}

  onSubmit() {
    if(this.userForm.valid == false){
      this.showNotification = true;
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      console.log('Form không hợp lệ!', this.userForm.controls);

    }else{
      
      if(this.users.some(user => user.username === this.userForm.value.username)){
        alert(this.userForm.value.username + ' existed, Please choose another username!');
      }else{
        const definedUserFormData = new FormData();
        definedUserFormData.append('username', this.userForm.value.username);
        definedUserFormData.append('password', this.userForm.value.password);
        definedUserFormData.append('email', this.userForm.value.email);
        definedUserFormData.append('role', this.userForm.value.role);
        definedUserFormData.append('name', this.userForm.value.name);
        definedUserFormData.append('birthday', this.userForm.value.birthday);
        definedUserFormData.append('imageFile', this.userForm.value.imageFile);
        definedUserFormData.append('major', this.userForm.value.major);
        definedUserFormData.append('file', this.selectedFile);

        this.userService.saveUser(definedUserFormData).subscribe(
          data =>{
            this.listUser();
          }
        ) 
        this.closeModal();  
      }
    }
  }

  listUser() {
    this.userService.getUserList().subscribe(
      data =>{
        this.users = data;
      }
    )
  }


  listMajor() {
    this.majorService.getMajorList().subscribe(
      data =>{
        this.majors = data;
      }
    )
  }

  filterByRole(role: string) {
    if(role === "ADMIN"){
      this.userService.getUserList().subscribe(data => {
        this.users = data.filter((user:User) => user.role === 'ADMIN');
      });
    }

    if(role === "STUDENT"){
      this.userService.getUserList().subscribe(data => {
        this.users = data.filter((user:User) => user.role === 'STUDENT');
      });
    }

    if(role === "TUTOR"){
      this.userService.getUserList().subscribe(data => {
        this.users = data.filter((user:User) => user.role === 'TUTOR');
      });
    }

    if(role === "ALL"){
      this.listUser();
    }
  }

  searchByUsername(){
    this.userService.getUserList().subscribe(data => {
      this.users = data.filter((user:User) => user.username === this.usernameSearch);
    });

  }

  deleteUserAccount(user:User) {
    if (confirm('Do you want to delete this account?')) {
      this.userService.deleteUser(user.id!).subscribe(
        {
          next: () => {
            console.log('Delete Successfully');
            this.listUser();
          },
          error: (error) => {
            console.error('Delete failedly:', error);
          }
        }
      )
  
      }
  
  }


  updateUserAccount(user: User) {
    this.userFormUpdate.setValue  ({
      username: user.username,
      password: user.password,
      email: user.email,
      role: user.role
    });
    this.userUpdateId = user.id!;
    this.openModalUpdate();

  }

  checkThisGuy(user: User){
    this.router.navigate(['user/searchblog'], { state: { data: user } });
  }

}
