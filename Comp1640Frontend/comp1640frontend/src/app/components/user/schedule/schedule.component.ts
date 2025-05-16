import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ScheduleService } from 'src/app/services/schedule.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/common/user';
import { forkJoin } from 'rxjs';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { Schedule } from 'src/app/common/schedule';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { Tutor } from 'src/app/common/tutor';
import { Student } from 'src/app/common/student';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Classroom } from 'src/app/common/classroom';
import { ClassroomService } from 'src/app/services/classroom.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  
  @ViewChild('myModalBookSchedule') modalBookSchedule: ElementRef | undefined;
  @ViewChild('myModalScheduleDetail') modalScheduleDetail: ElementRef | undefined;



  scheduleData: any[] = [];
  filteredSchedule: Schedule[] = [];

  selectedWeek: string = this.getCurrentWeek();;
  token = this.authService.getToken()!;
  userHold = {username:'',role:'',idStudentOrTutor:0,idUser:0};

  users: User[] = []
  tutors: Tutor[] = []
  students: Student[] = []
  classrooms: Classroom[] = []

  schedulesOfUser: Schedule[] = []
  schedulesOfStudent: Schedule[] = []
  scheduleAll: Schedule[] = []

  studentsOfTutor: Student[] = [];


  scheduleForm!: FormGroup;
  scheduleDetail!: Schedule

  minDateTime: string = '';


  fixedTimeSlots = [
    { start: '07:10', end: '08:40' },
    { start: '08:50', end: '10:20' },
    { start: '10:30', end: '12:00' },
    { start: '12:50', end: '14:20' },
    { start: '14:30', end: '16:00' }
  ];

  
  days = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

  roomsMain: string[] = [
    "R01", "R02", "R03", "R04", "R05",
    "R06", "R07", "R08", "R09", "R10",
    "R11", "R12", "R13", "R14", "R15",
    "R16", "R17", "R18", "R19", "R20"
  ];

  rooms: string[] = [];

  constructor(private http: HttpClient,
              private scheduleService: ScheduleService,
              private authService: AuthService,
              private userService: UserService,
              private jwtHelperService: JwtHelperService,
              private studentService: StudentService,
              private tutorService: TutorService,
              private classroomService: ClassroomService,
              private formBuilder: FormBuilder
              
  ) {}

  ngOnInit() {
    this.scheduleForm = this.formBuilder.group({
      studentDtoId: ['', [Validators.required]],
      startTime: ['', [Validators.required]],
      startDay: ['', [Validators.required]],
      scheduleFormat: ['', [Validators.required]],
      address: [''],

    });

    this.loadAllData();
  }


  loadAllData() {
    forkJoin({
      users: this.userService.getUserList(),
      tutors: this.tutorService.getTutorList(),
      students: this.studentService.getStudentList(),
      classrooms: this.classroomService.getClassroomList(),
      scheduleAll: this.scheduleService.getScheduleListAll()
    }).subscribe(({ users, tutors, students, classrooms, scheduleAll  }) => {
      this.users = users;
      this.tutors = tutors;
      this.students = students;
      this.classrooms = classrooms
      this.scheduleAll = scheduleAll;
      this.setUserHold();
      this.loadSchedule();
      this.setMinDateTime()
    });

  }

  getCurrentWeek(): string {
    const today = new Date();
    const date = new Date(Date.UTC(today.getFullYear(), today.getMonth(), today.getDate()));
    const dayNum = date.getUTCDay() || 7; // Chuẩn hóa: Chủ nhật = 7
  
    // Trừ để đưa về ngày Thứ Hai của tuần hiện tại
    date.setUTCDate(date.getUTCDate() + 4 - dayNum);
  
    const yearStart = new Date(Date.UTC(date.getUTCFullYear(), 0, 1));
    const weekNo = Math.ceil((((date.getTime() - yearStart.getTime()) / 86400000) + 1) / 7);
  
    return `${date.getUTCFullYear()}-W${weekNo.toString().padStart(2, '0')}`;
  }
  

  loadSchedule() {
    this.scheduleService.getScheduleList(this.userHold.idStudentOrTutor!).subscribe(
      data =>{
        if(data == null){
          this.schedulesOfUser = []
        }else{
          this.schedulesOfUser = data
          this.filterScheduleByWeek()
        }
      }
    ) 
  }

  setUserHold(){

    this.userHold.username = this.jwtHelperService.getUserFromToken(this.token).username
    this.userHold.role = this.jwtHelperService.getUserFromToken(this.token).role
    this.userHold.idUser = this.users.filter(user => user.username === this.userHold.username)[0].id!

    if(this.userHold.role === 'TUTOR'){
      const userIdTutorOrStudent = this.tutors.filter(tutor => tutor.username === this.userHold.username)[0].id
      this.userHold.idStudentOrTutor = userIdTutorOrStudent!
    }
    if(this.userHold.role === 'STUDENT'){
      const userIdTutorOrStudent = this.students.filter(student => student.username === this.userHold.username)[0].id
      this.userHold.idStudentOrTutor = userIdTutorOrStudent!
    }

  }


  onWeekChange(event: any) {
    this.selectedWeek = event.target.value;
    console.log(this.selectedWeek)

    this.filterScheduleByWeek();
  }


  filterScheduleByWeek() {
    if (this.selectedWeek) {
      this.filteredSchedule = this.schedulesOfUser.filter(item => item.weekOfYear?.toString() === this.selectedWeek);
      console.log( this.filteredSchedule)
      console.log( this.schedulesOfUser)

    } else {
      this.filteredSchedule = [];
    }
  }


  getScheduleForSlotAndDay(slot: string, day: string): Schedule {
    const entry = this.filteredSchedule.find(item => this.formatTime(item.startTime?.toString()!) === slot && item.dayOfWeek === day);

    if(this.userHold.role == 'STUDENT'){
      return entry ? entry : new Schedule(null,null,null,null,null,null,null,null,null,null);
    }else{
      return entry ? entry: new Schedule(null,null,null,null,null,null,null,null,null,null);
    }
  }



  formatTime(dateTime: string) {
    const date = new Date(dateTime);
    const hours = date.getHours().toString().padStart(2, '0'); // Lấy giờ
    const minutes = date.getMinutes().toString().padStart(2, '0'); // Lấy phút
    // console.log(`${hours}:${minutes}`)
    return `${hours}:${minutes}`; 
  }




  closeModalBookSchedule(){
    if (this.modalBookSchedule) {
      this.modalBookSchedule.nativeElement.style.display = 'none';
      this.scheduleForm.setValue({
        studentDtoId: '',
        startTime: '',
        startDay: '',
        scheduleFormat: '',
        address: ''
      });
      this.rooms = []
      this.schedulesOfStudent = []

    }else{
    console.log(123)
    }
  }

  openModalBookSchedule() {
    const modalElement = document.getElementById('myModalBookSchedule');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }

  closeModalScheduleDetail(){
    if (this.modalScheduleDetail) {
      this.modalScheduleDetail.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalScheduleDetail() {
    const modalElement = document.getElementById('myModalScheduleDetail');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }


  getStudentListOfTutor(){
    this.studentsOfTutor = []
    const classroomOfTutor = this.classrooms.filter(classroom => classroom.tutorId === this.userHold.idStudentOrTutor)
    if(classroomOfTutor.length === 0){
      alert('You dont have student to book schedule')
      return
    }
    for (let i = 0; i < classroomOfTutor[0].studentsId.length; i++) {
      this.studentsOfTutor.push(this.students.filter(student => student.id === classroomOfTutor[0].studentsId[i])[0])
    }
    this.openModalBookSchedule();
  }



  getListScheduleOfStudent(){
    this.schedulesOfStudent = []
    console.log(this.scheduleForm.value.studentDtoId)
    this.scheduleService.getStudentScheduleList(this.scheduleForm.value.studentDtoId).subscribe(
      data =>{
        if(data == null){
          this.schedulesOfStudent = []
        }else{
          this.schedulesOfStudent = data
        }
      }
    ) 
    

  }

  showBookScheduleForm(){
    this.getStudentListOfTutor();
  }

  saveSchedule(){
    if(this.scheduleForm.valid == false){
      alert("Enter enough information");

    }else{
      const selectedStartDayString = this.scheduleForm.value.startDay;

      const tutor = this.tutors.filter(tutor => tutor.username === this.userHold.username)[0];
      const student = this.students.filter(student => student.id === Number(this.scheduleForm.value.studentDtoId))[0];

      const startTime = this.scheduleForm.value.startTime;
      const endTime = this.fixedTimeSlots.find(slot => slot.start === startTime)?.end!;

      const startTimeDate =   `${selectedStartDayString}T${startTime}:00`

      console.log(startTimeDate);
      
      const endTimeDate =   `${selectedStartDayString}T${endTime}:00`

      if(this.schedulesOfUser.some(schedule => schedule.startTime?.toString() === startTimeDate)){
        alert("You had a class at this time");
        return;
      }

      if(this.schedulesOfStudent.some(schedule => schedule.startTime?.toString() === startTimeDate)){
        alert("Your student had a class at this time");
        return;
      }

      if (new Date(startTimeDate).getTime() < new Date(this.minDateTime).getTime()) {
        alert("You can set schedule in the past.");
        return;
      }

      if(this.scheduleForm.value.address === 'ONLINE'){
        this.scheduleForm.patchValue({
          address: ''
        });
      }

      this.scheduleService.bookSchedule(new Schedule(null, student, tutor, new Date(startTimeDate), new Date(endTimeDate),null,null, 'Not yet',this.scheduleForm.value.scheduleFormat,this.scheduleForm.value.address),this.checkboxValue).subscribe(
        data => {
          console.log(data);
          this.loadSchedule()
          this.closeModalBookSchedule();  // Đóng modal sau khi đặt lịch thành công
        },
        error => {
          console.error("Set schedule unsuccessfully!", error);
          alert("Set schedule unsuccessfully!");
        }
      );
    }
  }


  showDetailSchedule(schedule: Schedule){
    this.scheduleDetail = schedule;
    this.openModalScheduleDetail()
  }

  updateStatusSchedule(schedule: Schedule){

    this.setMinDateTime();
    const startTime = schedule.startTime + '';
    console.log(startTime);

    
    const fixedTime = new Date(this.minDateTime).getTime()



    if( new Date(startTime).getTime() > new Date(fixedTime).getTime()){
      alert('Chưa đến giờ điểm danh') 


    }else{
      if(schedule.status === ''){
        alert('123')
        this.loadSchedule()
      }else{
        console.log(schedule.status)
        this.scheduleService.updateSchedule(schedule, schedule.id!).subscribe(
          data =>{
            console.log(data)
            this.loadSchedule()
            alert('Set status successfully')
          }
        ) 
      }
    }


  }

  deteteSchedule(schedule: Schedule){
    console.log(schedule)
    if (confirm('Do you want to delete this schedule?')) {
      this.scheduleService.deleteSchedule(schedule.id!).subscribe(
        {
          next: () => {
            this.loadSchedule()
            alert('Delete schedule successfully')
            this.closeModalScheduleDetail();
          },
          error: (error) => {
            console.error('Delete failedly:', error);
          }
        }
      ) 
    }
  }

  findRemainRoomThisSlot(){
    const selectedStartDayString = this.scheduleForm.value.startDay;
    const startTime = this.scheduleForm.value.startTime
    const startTimeDate =   `${selectedStartDayString}T${startTime}:00`

    if(this.scheduleAll === null){
      this.rooms = this.roomsMain 

    }else{
      const roomsBooked = this.scheduleAll.filter(schedule => schedule.startTime?.toString() === startTimeDate && schedule.scheduleFormat === 'OFFLINE').map(schedule => schedule.address!);
      this.rooms = this.roomsMain.filter(room => !roomsBooked.includes(room))
      console.log(roomsBooked)
    }
  }


  setMinDateTime() {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset()); // Để tránh lệch múi giờ
    this.minDateTime = now.toISOString().slice(0, 16); // Format yyyy-MM-ddThh:mm
  }



checkboxValue: number = 1;
onCheckboxChange(event:any){
  if (event.target.checked) {
    this.checkboxValue = 4; 
  } else {
    this.checkboxValue = 1;
  }
}


}
