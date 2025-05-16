import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Admin } from 'src/app/common/admin';
import { Blog } from 'src/app/common/blog';
import { Classroom } from 'src/app/common/classroom';
import { Commentblog } from 'src/app/common/commentblog';
import { Major } from 'src/app/common/major';
import { Schedule } from 'src/app/common/schedule';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { Wbschatmessage } from 'src/app/common/wbschatmessage';
import { AdminserviceService } from 'src/app/services/adminservice.service';
import { AuthService } from 'src/app/services/auth.service';
import { BlogService } from 'src/app/services/blog.service';
import { BloglikesService } from 'src/app/services/bloglikes.service';
import { ClassroomService } from 'src/app/services/classroom.service';
import { CommentService } from 'src/app/services/comment.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { MajorService } from 'src/app/services/major.service';
import { ScheduleService } from 'src/app/services/schedule.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';
@Component({
  selector: 'app-blognologin',
  templateUrl: './blognologin.component.html',
  styleUrls: ['./blognologin.component.css']
})
export class BlognologinComponent implements OnInit {

  token: string = '';

  students: Student[] =[];
  admins: Admin[] = []

  commentBlogsAll: Commentblog[] = [];
  commentBlogsOfEachBlog: Commentblog[] = [];

  newComment: string = ''

  tutors: Tutor[] =[];
  users: User[] =[];

  blogs: Blog[] =[];

  content: string = '';  
  

  constructor(
              private studentService: StudentService,
              private tutorService: TutorService,
              private adminService: AdminserviceService,
              private blogService: BlogService,
              private userService: UserService,
              private bloglikesService: BloglikesService,
              private commentService: CommentService,
              private router: Router,
              private authService: AuthService,
              private jwtHelper: JwtHelperService
             
  ) { }

  ngOnInit(): void {
    this.token = this.authService.getToken()!;
    if(this.token){
      if(this.jwtHelper.getUserFromToken(this.token!).role === "ADMIN"){
        this.router.navigate(['/admin']);
      }else{
        this.router.navigate(['/user']);
      }
      
    }
    this.loadAllData()
  }


  loadAllData() {
    forkJoin({
      tutors: this.tutorService.getTutorList(),          // Lấy tutors
      students: this.studentService.getStudentList(),    // Lấy students
      blogs: this.blogService.getBlogList(),
      users: this.userService.getUserList(),
      admins: this.adminService.getAdminList()

    }).subscribe(({ tutors, students, users, blogs, admins  }) => {
      this.tutors = tutors;
      this.students = students;
      this.blogs = blogs
      this.users = users
      this.admins = admins
      this.listAllComments()      
      this.getApprovedBlogs()
    });
    
  }

  getApprovedBlogs() {
    this.blogs =  this.blogs.filter(blog => blog.status === 'APPROVED');
  }


  listAllComments(){
    this.commentService.getCommentList().subscribe(data => {
      this.commentBlogsAll = data
      console.log("heyyyyy")
    });
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



  toggleLike(blogId: number) {
    alert('please log in to like')
  }
  
  loadLikeCount(blogId: number) {
    this.bloglikesService.getLikeCount(blogId).subscribe(count => {
      const blog = this.blogs.find(b => b.id === blogId);
      if (blog) {
        blog.likeCount = count;
      }
    });
  }
  


  findUsernameById(id: number): string {
    const user = this.users.find(u => u.id === id);
    return user?.username!;
  }

  findUserImageByUsername(username: string){
    const user = this.users.filter(user => user.username === username)[0]

    if(user.role === 'STUDENT'){
      return this.students.filter(student => student.username === user.username)[0].imageFile
     }else{
       return this.tutors.filter(tutor => tutor.username === user.username)[0].imageFile
     }
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
  


  goToLoginPage(){
    this.router.navigate(['/login']);
  }







  
}
