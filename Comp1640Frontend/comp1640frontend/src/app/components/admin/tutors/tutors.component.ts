import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Major } from 'src/app/common/major';
import { Tutor } from 'src/app/common/tutor';
import { MajorService } from 'src/app/services/major.service';
import { TutorService } from 'src/app/services/tutor.service';

@Component({
  selector: 'app-tutors',
  templateUrl: './tutors.component.html',
  styleUrls: ['./tutors.component.css']
})
export class TutorsComponent implements OnInit {


@ViewChild('myModalUpdateTutor') modalUpdateTutor: ElementRef | undefined;


  tutorFormUpdate!: FormGroup;
  tutor!: Tutor;
  selectedFile!: File;
  majors: Major[] =[];
  showNotification = false;
  tutors: Tutor[] =[];

  constructor(private formBuilder: FormBuilder,
              private majorService: MajorService,
              private tutorService: TutorService) { }



  ngOnInit(): void {
          this.tutorFormUpdate = this.formBuilder.group({
            nameUpdate: ['', [Validators.required, Validators.minLength(3)]],
            birthdayUpdate: ['', [Validators.required, Validators.minLength(3)]],
            majorUpdate: ['', [Validators.required]],
            usernameUpdate: []
          });
    this.listTutor();
    this.listMajor();
  }

  


  listTutor() {
    this.tutorService.getTutorList().subscribe(
      data =>{
        this.tutors = data;
        console.log(this.tutors);
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

    if(this.tutorFormUpdate.valid == false){
      this.showNotification = true;
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      console.log('Form không hợp lệ!', this.tutorFormUpdate.controls);

    }else{
        const definedTutorFormData = new FormData();
        definedTutorFormData.append('id', this.tutor.id! + "");
        
        definedTutorFormData.append('name', this.tutorFormUpdate.value.nameUpdate);
        definedTutorFormData.append('birthday', this.tutorFormUpdate.value.birthdayUpdate);
        definedTutorFormData.append('imageFile', this.tutor.imageFile!);
        definedTutorFormData.append('username', this.tutor.username!);
        definedTutorFormData.append('majorDtoId', this.tutorFormUpdate.value.majorUpdate);
        definedTutorFormData.append('file', this.selectedFile);


        this.tutorService.updateTutor(definedTutorFormData,this.tutor.id!).subscribe(
          data =>{
            this.listTutor();
            console.log(data)
          }
        ) 
        this.closeModalUpdate();  
    }
  }

  closeModalUpdate(){
    if (this.modalUpdateTutor) {
      this.modalUpdateTutor.nativeElement.style.display = 'none';
    }
  }

  openModalUpdate() {
    const modalElement = document.getElementById('myModalUpdateTutor');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }


  updateTutorAccount(tutor: Tutor) {
      this.tutorFormUpdate.patchValue  ({
        nameUpdate: tutor.name,
        birthdayUpdate: tutor.birthday,
        majorUpdate: tutor.majorDto?.id
      });
      this.tutor = tutor;
      this.openModalUpdate()
  }

}
