import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Admin } from 'src/app/common/admin';
import { Blog } from 'src/app/common/blog';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { AdminserviceService } from 'src/app/services/adminservice.service';
import { BlogService } from 'src/app/services/blog.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-approveblog',
  templateUrl: './approveblog.component.html',
  styleUrls: ['./approveblog.component.css']
})
export class ApproveblogComponent implements OnInit {


  students: Student[] =[];
  admins: Admin[] = []
  newComment: string = ''
  tutors: Tutor[] =[];
  users: User[] =[];
  blogs: Blog[] =[];

  constructor(
                  private studentService: StudentService,
                  private tutorService: TutorService,
                  private adminService: AdminserviceService,
                  private blogService: BlogService,
                  private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.loadAllData();

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
        this.getWaittingBlogs();
      });
    }

    getWaittingBlogs() {
      this.blogs =  this.blogs.filter(blog => blog.status !== 'APPROVED');
    }

    approveBlog(blog: Blog){

      const blogFormDataUpdate = new FormData();
      blogFormDataUpdate.append('status', 'APPROVED');
      blogFormDataUpdate.append('content', blog.content!);
      blogFormDataUpdate.append('fileName', blog.fileName!);

      this.blogService.updateBlog(blogFormDataUpdate,blog.id!).subscribe(
        data =>{
          console.log(data);
          this.loadAllData();
        }
      ) 

    }

}
