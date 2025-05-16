import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Major } from 'src/app/common/major';
import { Student } from 'src/app/common/student';
import { AuthService } from 'src/app/services/auth.service';
import { MajorService } from 'src/app/services/major.service';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {

@ViewChild('myModalUpdateStudent') modalUpdateStudent: ElementRef | undefined;


  studentFormUpdate!: FormGroup;
  student!: Student;
  selectedFile!: File;
  majors: Major[] =[];
  showNotification = false;
  students: Student[] =[];

  constructor(private formBuilder: FormBuilder,
              private majorService: MajorService,
              private studentService: StudentService,
              private authService: AuthService) { }



  ngOnInit(): void {

        console.log(this.authService.getToken())

          this.studentFormUpdate = this.formBuilder.group({
            nameUpdate: ['', [Validators.required, Validators.minLength(3)]],
            birthdayUpdate: ['', [Validators.required, Validators.minLength(3)]],
            majorUpdate: ['', [Validators.required]],
            usernameUpdate: []
          });
    this.listStudents();
    this.listMajor();
  }


  listStudents() {
    this.studentService.getStudentList().subscribe(
      data =>{
        this.students = data;
        console.log(this.students);
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

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmitUpdate(){

    if(this.studentFormUpdate.valid == false){
      this.showNotification = true;
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      console.log('Form không hợp lệ!', this.studentFormUpdate.controls);

    }else{
        const definedStudentFormData = new FormData();
        definedStudentFormData.append('id', this.student.id! + "");

        definedStudentFormData.append('name', this.studentFormUpdate.value.nameUpdate);
        definedStudentFormData.append('birthday', this.studentFormUpdate.value.birthdayUpdate);
        definedStudentFormData.append('imageFile', this.student.imageFile!);
        definedStudentFormData.append('username', this.student.username!);
        definedStudentFormData.append('majorDtoId', this.studentFormUpdate.value.majorUpdate);
        definedStudentFormData.append('file', this.selectedFile);

        definedStudentFormData.forEach((value, key) => {
          console.log(key, value);
        });

        this.studentService.updateStudent(definedStudentFormData,this.student.id!).subscribe(
          data =>{
            this.listStudents();
            console.log(data)
          }
        ) 
        this.closeModalUpdate();  
    }
  }

  closeModalUpdate(){
    if (this.modalUpdateStudent) {
      this.modalUpdateStudent.nativeElement.style.display = 'none';
    }
  }

  openModalUpdate() {
    const modalElement = document.getElementById('myModalUpdateStudent');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }


    updateStudentAccount(student: Student) {
      this.studentFormUpdate.patchValue  ({
        nameUpdate: student.name,
        birthdayUpdate: student.birthday,
        majorUpdate: student.majorDto?.id
      });
      this.student = student;
      this.openModalUpdate()
    }
  

}
