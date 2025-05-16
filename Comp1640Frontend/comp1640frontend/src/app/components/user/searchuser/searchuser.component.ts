import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Classroom } from 'src/app/common/classroom';
import { District } from 'src/app/common/district';
import { Friendrequest } from 'src/app/common/friendrequest';
import { Friendrequestdto } from 'src/app/common/friendrequestdto';
import { Grade } from 'src/app/common/grade';
import { Major } from 'src/app/common/major';
import { Majorgrade } from 'src/app/common/majorgrade';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { ClassroomService } from 'src/app/services/classroom.service';
import { DistrictService } from 'src/app/services/district.service';
import { GradeService } from 'src/app/services/grade.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { MajorService } from 'src/app/services/major.service';
import { MajorgradeService } from 'src/app/services/majorgrade.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-searchuser',
  templateUrl: './searchuser.component.html',
  styleUrls: ['./searchuser.component.css']
})
export class SearchuserComponent implements OnInit {

  userHold = {username:'',role:'',idStudentOrTutor:0,idUser:0,emailUser:'',idDistrict:0,imageFile:null as string | null};

  searchTutorOrStudent = {username:null as string | null,districtId:null as number | null,gradeId:null as number | null,majorId:null as number | null}



  tutors: Tutor[] =[];
  users: User[] =[];
  usersNoAdmin: User[] =[];
  tutorsOnly: User[] = []
  studentsOnly: User[] = []


  classrooms: Classroom[] = []
  
  grades: Grade[] = []
  majors: Major[] = []

  filterMajorsById: Major[] = []

  majorGrades: Majorgrade[] = [];

  students: Student[] =[];
  districts: District[] = [];
  token = this.authService.getToken()!;
  allFriendRequestDtos: Friendrequestdto[] = []

  constructor(  private router: Router,
                private studentService: StudentService,
                private tutorService: TutorService,
                private userService: UserService,
                private districtService: DistrictService,
                private websocketService: WebsocketService, 
                private jwtHelperService: JwtHelperService,
                private authService: AuthService,
                private classroomService: ClassroomService,
                private gradeService: GradeService,
                private majorService: MajorService,
                private majorGradeService: MajorgradeService
              ) { }

  ngOnInit(): void {
    this.loadAllData();
  }


    loadAllData() {
      forkJoin({
        tutors: this.tutorService.getTutorList(),          // Lấy tutors
        students: this.studentService.getStudentList(),    // Lấy students
        users: this.userService.getUserList(),
        districts: this.districtService.getDistrictList(),
        allFriendRequestDtos: this.classroomService.getAllFriendrequest(),
        grades: this. gradeService.getGradeList(),
        majors: this.majorService.getMajorList(),
        majorGrades: this.majorGradeService.getMajorGraderlList(),
        classrooms: this.classroomService.getClassroomList()
      }).subscribe(({ tutors, students, users, districts,allFriendRequestDtos,grades,majors,majorGrades,classrooms  }) => {
        this.tutors = tutors;
        this.students = students;
        this.users = users;
        this.usersNoAdmin = this.users.filter(user => user.role !== 'ADMIN');
        this.districts = districts
        this.allFriendRequestDtos = allFriendRequestDtos
        this.grades = grades
        this.majors = majors
        this.majorGrades = majorGrades
        this.classrooms = classrooms
        this.setUserHold()
      });
    }

    getallFriendRequestDtos(){
      this.classroomService.getAllFriendrequest().subscribe(
        data =>{
          this.allFriendRequestDtos = data
        }
      )
    }

    findObjectByUsername(username: string, role: string):any{
      if(role === 'STUDENT'){
        console.log(username + role )
        return this.students.filter(student => student.username === username)[0]
       }
      if(role === 'TUTOR'){
        console.log(username + role )

         return this.tutors.filter(tutor => tutor.username === username)[0]
       }
    }

    findObjectByUserId(userId: Number):any{
      const user = this.users.filter(user => user.id === userId)[0]
  
      if(user.role === 'STUDENT'){
        return this.students.filter(student => student.username === user.username)[0]
       }
      if(user.role === 'TUTOR'){
         return this.tutors.filter(tutor => tutor.username === user.username)[0]
       }
    }
  

    sendAddFriendRequest( recipient:string){
      this.websocketService.sendAddFriendRequest(this.userHold.username,recipient)
      window.location.reload()
    }

    checkAddFriendRequest(user: User) : boolean{  
      const found = this.allFriendRequestDtos.some(
        friendRequestDto => (friendRequestDto.senderId === user.id && friendRequestDto.recipientId === this.userHold.idUser) ||
                            (friendRequestDto.senderId === this.userHold.idUser && friendRequestDto.recipientId === user.id));
      
      console.log(found)
      console.log(this.allFriendRequestDtos)
        
      return found;
    }

    setTutorsOnly():any {
      const myClassroom = this.classrooms.filter(classroom => classroom.studentsId.includes(this.userHold.idStudentOrTutor))
      const tutorIds = myClassroom.map(classroom => classroom.tutorId);

      if (myClassroom) {
        return this.usersNoAdmin.filter(user => user.role === 'TUTOR' && 
          tutorIds.includes(this.findObjectByUserId(user.id!).id) === false && this.findObjectByUserId(user.id!).status ==='PRIME');
      } else {
        return this.usersNoAdmin.filter(user => user.role === 'TUTOR' && this.findObjectByUserId(user.id!).status ==='PRIME');
      }
      
    }

    setStudentOnly():any {
      const myClassroom = this.classrooms.find(classroom => classroom.tutorId = this.userHold.idStudentOrTutor);
      if (myClassroom) {
      return this.usersNoAdmin.filter(user => user.role === 'STUDENT' && 
        myClassroom?.studentsId.includes(this.findObjectByUserId(user.id!).id) === false);
      }else{
        return this.usersNoAdmin.filter(user => user.role === 'STUDENT');
      }
    }
    
  
    setUserHold(){

      this.userHold.username = this.jwtHelperService.getUserFromToken(this.token).username
      this.userHold.role = this.jwtHelperService.getUserFromToken(this.token).role
      this.userHold.idUser = this.users.filter(user => user.username === this.userHold.username)[0].id!
      this.userHold.emailUser = this.users.filter(user => user.username === this.userHold.username)[0].email!
      this.userHold.idDistrict = this.users.filter(user => user.username === this.userHold.username)[0].districtId!
  
      if(this.userHold.role === 'TUTOR'){
        const userTutorOrStudent = this.tutors.filter(tutor => tutor.username === this.userHold.username)[0]
        this.userHold.idStudentOrTutor = userTutorOrStudent.id!
        this.userHold.imageFile = userTutorOrStudent.imageFile ?? null
        this.studentsOnly = this.setStudentOnly();
      }
      if(this.userHold.role === 'STUDENT'){
        const userTutorOrStudent = this.students.filter(student => student.username === this.userHold.username)[0]
        this.userHold.idStudentOrTutor = userTutorOrStudent.id!
        this.userHold.imageFile = userTutorOrStudent.imageFile ?? null
        this.tutorsOnly = this.setTutorsOnly();

      }
    }

    checkThisGuy(user: User){
      this.router.navigate(['user/searchblog'], { state: { data: user } });
    }


    findMajorByGradeId(gradeId: number){
      this.filterMajorsById = []
      const filterMajorGradesById = this.majorGrades.filter(majorGrade => majorGrade.gradeId === gradeId)
      console.log(filterMajorGradesById)

      for(const majorGrade of filterMajorGradesById ){
        const major = this.majors.find(major => major.id === majorGrade.majorId)!
        this.filterMajorsById.push(major);
      }
    }

    findStudentOrTutor(){
      if(this.userHold.role === 'TUTOR'){
        
        if(this.searchTutorOrStudent.username ===''){
          this.searchTutorOrStudent.username = null
        }

        this.userService.findStudent(this.searchTutorOrStudent).subscribe(
          data=>  {
            console.log(data)
            this.studentsOnly = data
          })
      }else{

        if(this.searchTutorOrStudent.username ===''){
          this.searchTutorOrStudent.username = null
        }

        this.userService.findTutor(this.searchTutorOrStudent).subscribe(
        data=>  {
          console.log(data)
          this.tutorsOnly = data
        })

      }


    }

}
