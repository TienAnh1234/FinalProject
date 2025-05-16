import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Admin } from 'src/app/common/admin';
import { Blog } from 'src/app/common/blog';
import { Commentblog } from 'src/app/common/commentblog';
import { District } from 'src/app/common/district';
import { Grade } from 'src/app/common/grade';
import { Major } from 'src/app/common/major';
import { Majorgrade } from 'src/app/common/majorgrade';
import { Majorgradetutor } from 'src/app/common/majorgradetutor';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { AdminserviceService } from 'src/app/services/adminservice.service';
import { AuthService } from 'src/app/services/auth.service';
import { BlogService } from 'src/app/services/blog.service';
import { BloglikesService } from 'src/app/services/bloglikes.service';
import { CommentService } from 'src/app/services/comment.service';
import { DistrictService } from 'src/app/services/district.service';
import { GradeService } from 'src/app/services/grade.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { MajorService } from 'src/app/services/major.service';
import { MajorgradeService } from 'src/app/services/majorgrade.service';
import { MajorgradetutorService } from 'src/app/services/majorgradetutor.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-searchblog',
  templateUrl: './searchblog.component.html',
  styleUrls: ['./searchblog.component.css']
})
export class SearchblogComponent implements OnInit {

  
  users: User[] = [];
  tutors: Tutor[] = [];
  students: Student[] = [];
  admins: Admin[] = []
  districts: District[] = [];
  blogsOfThisUser: Blog[] = []
  blogs: Blog[] =[];
  commentBlogsAll: Commentblog[] = [];
  commentBlogsOfEachBlog: Commentblog[] = [];

  newComment: string = ''


  myMajorgradetutor: Majorgradetutor[] = [];
  majorGrades: Majorgrade[] = [];
  majors: Major[] = [];
  grades: Grade[] = [];


  userSearch!: User
  userHold = {username:'',role:'',idStudentOrTutor:0,idUser:0,emailUser:'',idDistrict:0,imageFile:null as string | null};
  token = this.authService.getToken()!;


  constructor(private router: Router,
              private jwtHelperService: JwtHelperService,
              private authService: AuthService,
              private userService: UserService,
              private tutorService: TutorService,
              private adminService: AdminserviceService,
              private studentService: StudentService,
              private districtService: DistrictService,
              private blogService: BlogService,
              private bloglikesService: BloglikesService, 
              private commentService: CommentService,
              private gradeService: GradeService,
              private majorService: MajorService,
              private majorgradeService: MajorgradeService,
              private majorgradetutorService: MajorgradetutorService,

              
  ) {   
  }

  ngOnInit(): void {
    this.userSearch = history.state.data;
    console.log(this.userSearch);
    this.loadAllData()
  }

  loadAllData() {
    forkJoin({
      blogs: this.blogService.getBlogList(),
      users: this.userService.getUserList(),
      tutors: this.tutorService.getTutorList(),
      students: this.studentService.getStudentList(),
      admins: this.adminService.getAdminList(),
      districts: this.districtService.getDistrictList(),
      majors: this.majorService.getMajorList(),
      grades: this.gradeService.getGradeList(),
      majorgrades: this.majorgradeService.getMajorGraderlList()
    }).subscribe(({blogs,users,tutors,students,districts,majors,grades,majorgrades,admins}) => {
      this.blogs = blogs
      this.users = users
      this.tutors = tutors
      this.students = students
      this.admins = admins
      this.districts = districts
      this.majorGrades = majorgrades
      this.majors = majors
      this.grades = grades
      this.setUserHold();
      this.findBlogsOfThisUser()
      this.setLikeNumberOfBlogs()

      if(this.userSearch.role === 'TUTOR'){
        this.listMajorGradeTutorByTutorId()
      }
      

    });
    
  }





  listMajorGradeTutorByTutorId(){
    this.majorgradetutorService.getAllMajorgradeTutorDtoByTutorId(this.findObjectByUsername(this.userSearch.username!).id).subscribe(data => {
      this.myMajorgradetutor = data
      console.log(this.myMajorgradetutor)
      this.findMajorByTutorId()
    });
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
    }
    if(this.userHold.role === 'STUDENT'){
      const userTutorOrStudent = this.students.filter(student => student.username === this.userHold.username)[0]
      this.userHold.idStudentOrTutor = userTutorOrStudent.id!
      this.userHold.imageFile = userTutorOrStudent.imageFile ?? null
    }
  }

  findObjectByUsername(username: string):any{
    if(this.userSearch.role === 'STUDENT'){
      return this.students.filter(student => student.username === username)[0]
     }
    if(this.userSearch.role === 'TUTOR'){
       return this.tutors.filter(tutor => tutor.username === username)[0]
     }
     if(this.userSearch.role === 'ADMIN'){
      return this.admins.filter(admin => admin.username === username)[0]
    }
  }

  findDistrictById(id: Number):any{
    return this.districts.filter(district => district.id === id)[0]
  }

  findBlogsOfThisUser(){
    this.blogsOfThisUser = this.blogs.filter(blog => blog.userDto?.id === this.userSearch.id! && blog.status === 'APPROVED')
  }


  findObjectByUser(user: User):any{
    if(user.role === 'STUDENT'){
     return this.students.filter(student => student.username === user.username)[0]
    }
    if(user.role === 'TUTOR'){
      return this.tutors.filter(tutor => tutor.username === user.username)[0]
    }
    if(user.role === 'ADMIN'){
      return this.admins.filter(admin => admin.username === user.username)[0]
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


  toggleLike(blogId: number) {
    const user = this.users.filter(user => user.username === this.userHold.username)
    const userId = user[0].id!; // Ví dụ, lấy từ token hoặc AuthService
  
    this.bloglikesService.toggleLike(userId, blogId).subscribe(res => {
      console.log(res);
      this.loadLikeCount(blogId); // reload lại số like sau khi bấm
    });
  }
  
  loadLikeCount(blogId: number) {
    this.bloglikesService.getLikeCount(blogId).subscribe(count => {
      const blog = this.blogs.find(b => b.id === blogId);
      if (blog) {
        blog.likeCount = count;
      }
    });
  }


  visibleComments: Set<number> = new Set();


  showComment(blogId: number) {

    if (this.visibleComments.has(blogId)) {
      this.visibleComments.delete(blogId);
      return;
    }

    this.commentService.getCommentList().subscribe(data => {
      this.commentBlogsAll = data;
  
      this.commentBlogsOfEachBlog = this.commentBlogsAll.filter(comment => comment.blogId === blogId);
      console.log(this.commentBlogsOfEachBlog);
  
      if (this.commentBlogsOfEachBlog.length === 0) {
        console.log(123);
        return;
      } else {
        const blog = this.blogs.find(blog => blog.id === blogId);
        if (blog) {
          blog.comments = this.commentBlogsOfEachBlog;
          this.visibleComments.add(blogId);
        }
      }
    });
  }

  handleInput(event: Event) {
    this.newComment = (event.target as HTMLInputElement).value;
  }

  submitComment(blogId: number){

    if(this.newComment === ''){
      alert('Please enter something')
      return
    }

    const user = this.users.filter(user => user.username === this.userHold.username)
    const userId = user[0].id!; 
    const commentBlog = new Commentblog(null, this.newComment, null, blogId,userId)
    
    this.commentService.saveComment(commentBlog).subscribe(
      data =>{
        console.log(data)
        this.newComment =''
        this.showComment(blogId);
      }
    )
  }

  setLikeNumberOfBlogs(){
    for (let blog of this.blogs) {
      this.loadLikeCount(blog.id!)
    }
  }


    myMajorGradeListName: { name?: string; grade?: string }[] = [];
    findMajorByTutorId(){
      for (const majorgradetutor of this.myMajorgradetutor) {
        const majorGrade = this.majorGrades.find(majorGrade => majorGrade.id === majorgradetutor.majorGradeId)!
        const majorName =  this.majors.find(major => major.id === majorGrade?.majorId)?.name
        const gradeName =  this.grades.find(grade => grade.id === majorGrade?.gradeId)?.name
        this.myMajorGradeListName.push({name: majorName, grade: gradeName });
      }
      console.log(this.myMajorGradeListName)
    }

    deleteBlog(idBlog: number){

      if((confirm('Do you want to delete this blog?'))){
        this.blogService.deleteBlog(idBlog).subscribe(
            data =>{
              // console.log(data);
              this.loadAllData()
            }) 
      }
    }
    

}
