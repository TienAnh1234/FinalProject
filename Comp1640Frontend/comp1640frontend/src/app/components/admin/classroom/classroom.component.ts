import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Classroom } from 'src/app/common/classroom';
import { ClassroomMain } from 'src/app/common/classroom-main';
import { Major } from 'src/app/common/major';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { ClassroomService } from 'src/app/services/classroom.service';
import { MajorService } from 'src/app/services/major.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { forkJoin } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { ScheduleService } from 'src/app/services/schedule.service';

@Component({
  selector: 'app-classroom',
  templateUrl: './classroom.component.html',
  styleUrls: ['./classroom.component.css']
})
export class ClassroomComponent implements OnInit {

@ViewChild('myModalShowClassroomInfor') myModalShowClassroomInfor: ElementRef | undefined;

  isCreateOrUpdateMode: boolean = true;
  majors: Major[] =[];
  tutors: Tutor[] =[];
  classroomMains : ClassroomMain[] = [];

  classrooms: Classroom[] = []
  studentsId: number[] = []

  students: Student[] =[];
  
  
  selectedMajorId!: number;
  
  filteredTutors: Tutor[] = [];
  showTutor: Boolean= false;
  tutorChoosed: Tutor | null = null;
  tutorExisted: Tutor[] = [];


  filteredStudents: Student[] = [];
  showStudent: Boolean= false;
  studentsChoosed: Student[] = [];

  currentPageStudent = 1;


  constructor(private majorService: MajorService,
              private tutorService: TutorService,
              private classroomService: ClassroomService,
              private studentService: StudentService,
              private authService: AuthService,
              private scheduleService: ScheduleService
  ) { }

  ngOnInit(): void {

    // console.log(this.authService.getToken)
    // this.authService.logout();
    this.loadAllData();
      // this.listMajor();
      // this.listTutors();
      // this.listStudents();
      // this.listClassroom();
  }

  loadAllData() {
    forkJoin({
      majors: this.majorService.getMajorList(),          // Nếu cần majors
      tutors: this.tutorService.getTutorList(),          // Lấy tutors
      students: this.studentService.getStudentList(),    // Lấy students
      classrooms: this.classroomService.getClassroomList() // Lấy classrooms
    }).subscribe(({ majors, tutors, students, classrooms }) => {
      this.majors = majors;
      this.tutors = tutors;
      this.students = students;
      this.classrooms = classrooms;
      this.processClassroomData();
    });
  }

  changeClassroomToClassroomMain() {
    this.classroomMains = [];
    for (const classroom of this.classrooms) {
      let tutor = this.tutors.find(tutor => tutor.id === classroom.tutorId);
      for (const studentId of classroom.studentsId) {
        let student = this.students.find(student => student.id === studentId);
        this.classroomMains.push({ id: null, tutor: tutor!, student: student! });
        }
      }
      console.log(this.classroomMains)
  }

  openModalUpdateClassroom() {
    const modalElement = document.getElementById('myModalShowClassroomInfor');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }
  
  closeModalUpdateClassroom() {
    if (this.myModalShowClassroomInfor) {
      this.myModalShowClassroomInfor.nativeElement.style.display = 'none';
    }
  }
  
  processClassroomData() {
    this.changeClassroomToClassroomMain(); 

    this.tutorExisted = [];
  
    for (const classroom of this.classrooms) {
      if (!classroom?.tutorId) continue;
  
      const tutor = this.tutors.find(tutor => tutor.id === classroom.tutorId);
      if (tutor) {
        this.tutorExisted.push(tutor);
      } else {
        console.warn(`Không tìm thấy tutor với id: ${classroom.tutorId}`);
      }
    }
    
    console.log('Tutor Existed:', this.tutorExisted);
  }




  chooseMajor(event: Event) {
    const selectedId = (event.target as HTMLSelectElement).value;
    this.selectedMajorId = Number(selectedId); // Chuyển thành số
    console.log('Selected Major ID:', this.selectedMajorId);
    this.filteredTutors = this.tutors.filter(tutor => tutor.majorDto?.id === this.selectedMajorId && !this.tutorExisted.includes(tutor) );
    this.filteredStudents = this.students.filter(student => student.majorDto?.id === this.selectedMajorId);
    console.log(this.tutors)

    if(this.filteredTutors.length >= 1 && this.isCreateOrUpdateMode == true){
      console.log(this.filteredTutors)
      this.showTutor = true;
    }else{
      this.showTutor = false;

    }

    if(this.filteredStudents.length >= 1 && this.isCreateOrUpdateMode == true){
      console.log(this.filteredTutors)
      this.showStudent = true;
    }else{
      this.showStudent = false;

    }

    // if(this.filteredStudents.length >= 1){
    //   console.log(this.filteredStudents)
    //   this.showStudent = true;
    // }
  }

  closeClassroomInfo() {
    // this.showTutor = true;
    this.showStudent = false;
    this.isCreateOrUpdateMode = true;
    this.tutorChoosed = null;
    this.studentsChoosed = [];
  }


  chooseTutor(tutor: Tutor) {
    this.tutorChoosed = tutor;
    // this.showStudent = true;
    console.log(tutor.id);
  }

  removeTutor(){
    this.tutorChoosed = null;
    this.studentsChoosed =[]
  }

  chooseStudent(student: Student) {
    if (!this.studentsChoosed.includes(student)) {
      if(student.majorDto!.id != this.tutorChoosed?.majorDto!.id){
        alert('Please choose student having the same major with tutor')
      }
      else{
      this.studentsChoosed.push(student);
      }
    }
  }

  removeStudent(student: Student){
    this.studentsChoosed = this.studentsChoosed.filter(s => s !== student);
  }

  createClass(){

    if(this.studentsChoosed.length == 0  || this.tutorChoosed == null){
      alert('Please enter all information')
      
    }else{
        for (let student of this.studentsChoosed) {
          this.studentsId.push(student.id!)
        }
        const newClassroom = new Classroom(null,this.tutorChoosed.id!,this.studentsId);

        this.classroomService.saveClassroom(newClassroom).subscribe(
          data =>{
            console.log(data);
            window.location.reload();
          }
        ) 
      

    }

  }


  openUpdateClassroom(tutor: Tutor){
    this.isCreateOrUpdateMode = false;
    this.showTutor = false;

    this.tutorChoosed = tutor;
    this.studentsChoosed =[]
    for (let classroomMain of this.classroomMains) {
      if(classroomMain.tutor!.id == tutor.id ){
        this.studentsChoosed.push(classroomMain.student!)
      }
    }
    
  }

  
  updateClass(){
    if(this.studentsChoosed.length == 0  ){
      this.studentsChoosed =[]
      alert('Please choose at least one student')
    }else{
      this.studentsId = [];
      for (let student of this.studentsChoosed) {
        this.studentsId.push(student.id!)
      }
      const newClassroom = new Classroom(null,this.tutorChoosed!.id!,this.studentsId);

      this.classroomService.updateClassroom(newClassroom).subscribe(
        data =>{
          console.log(data);
          this.loadAllData()
          this.changeClassroomToClassroomMain()
          this.closeClassroomInfo()
        }
      ) 
    }
    // this.loadAllData()
    // this.changeClassroomToClassroomMain()
    // window.location.reload();

  }

  deleteClassOfTutor(tutor:Tutor){
    if (confirm('Do you want to delete this major?')) {
      this.classroomService.deleteClassOfTutor(tutor.id!).subscribe(
        data =>{
          window.location.reload();
        }
      )
    }else{
      window.location.reload();
    }
  } 




}
