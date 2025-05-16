import { Component, OnInit } from '@angular/core';
import { ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Major } from 'src/app/common/major';
import { MajorService } from 'src/app/services/major.service';

@Component({
  selector: 'app-majors',
  templateUrl: './majors.component.html',
  styleUrls: ['./majors.component.css']
})
export class MajorsComponent implements OnInit {

  @ViewChild('myModal') model: ElementRef | undefined;

  majorForm!: FormGroup;
  majors: Major[] =[];

  checkSaveOrUpdate: number = 0;
  majorUpdateId!: number;


  constructor(private formBuilder: FormBuilder,
              private majorService: MajorService,
              private router: Router
  ) { }

  ngOnInit(): void {
    this.majorForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(3)]]
    });
    this.listMajor();
  }


  openModal() {
    const modalElement = document.getElementById('myModal');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }

  closeModal() {
    if (this.model) {
      this.model.nativeElement.style.display = 'none';
    }
    // (document.getElementById('majorName') as HTMLInputElement).value ='';
    // (document.getElementById('description') as HTMLInputElement).value ='';

    this.majorForm.setValue({
      name: '',
      description: ''
    });

    this.checkSaveOrUpdate = 0
  }


  onSubmit(){

    const newMajor: Major = {
      name: this.majorForm.value.name,
      description: this.majorForm.value.description
    };
    console.log(newMajor);


    if(this.checkSaveOrUpdate == 0){
      this.majorService.saveMajor(newMajor).subscribe(
        data =>{
          this.listMajor();
        }
      ) 
      this.closeModal();  
    }

    if(this.checkSaveOrUpdate == 1){
      this.majorService.updateMajor(newMajor, this.majorUpdateId).subscribe(
        data =>{
          console.log(data);  
          this.listMajor();
        }
      )
      this.closeModal();  
    }


  }

  listMajor() {
    this.majorService.getMajorList().subscribe(
      data =>{
        this.majors = data;
      }
    )
  }


  updateMajor(major: Major){
    this.checkSaveOrUpdate = 1;
    // (document.getElementById('majorName') as HTMLInputElement).value = major.name + '';
    // (document.getElementById('description') as HTMLInputElement).value = major.description + '';

    this.majorForm.setValue({
      name: major.name,
      description: major.description
    });

    console.log(major.name);
    console.log(major.description);

    console.log(this.majorForm.value.name);
    console.log(this.majorForm.value.description);
    
    this.majorUpdateId = major.id!;
    this.openModal();
  }



  
  deleteMajor(major: Major){

    if (confirm('Do you want to delete this major?')) {

    this.majorService.deleteMajor(major.id!).subscribe(
      {
        next: () => {
          console.log('Delete Successfully');
          this.listMajor();
        },
        error: (error) => {
          console.error('Delete failedly:', error);
        }
      }
    )

    }


  }
}
