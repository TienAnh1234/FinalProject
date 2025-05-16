import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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

@Component({
  selector: 'app-myblog',
  templateUrl: './myblog.component.html',
  styleUrls: ['./myblog.component.css']
})
export class MyblogComponent implements OnInit {


  @ViewChild('myModalUpdateBlog') myModalUpdateBlog: ElementRef | undefined;
  @ViewChild('fileInput') fileInput!: ElementRef;
  @ViewChild('fileInputNew') fileInputNew!: ElementRef;

  @ViewChild('myModalUpdateUser') myModalUpdateUser: ElementRef | undefined;
  @ViewChild('myModalUpdateComment') modalUpdateComment: ElementRef | undefined;
  @ViewChild('myModalChooseMajor') modalChooseMajor: ElementRef | undefined;



  users: User[] = [];
  tutors: Tutor[] = [];
  students: Student[] = [];
  admins: Admin[] = []
  districts: District[] = [];
  myMajorgradetutor: Majorgradetutor[] = [];



  grades: Grade[] = [];
  gradesLeft: Grade[] = [];
  gradesRight: Grade[] = [];


  majors: Major[] = [];
  majorGrades: Majorgrade[] = [];


  commentBlogsAll: Commentblog[] = [];
  commentBlogsOfEachBlog: Commentblog[] = [];

  newComment: string = ''

  content: string = '';  


  userFormUpdate!: FormGroup;

  
  blogs: Blog[] =[];
  myBlogs: Blog[] = [];
  userHold = {username:'',role:'',idStudentOrTutor:0,idUser:0,emailUser:'https://www.gravatar.com/avatar/?d=mp&s=100',idDistrict:0,imageFile:null as string | null};
  token = this.authService.getToken()!;
  selectedFile: File | null = null;
  selectedImageFile: File | null = null;


  blogUpdate?: Blog;
  constructor(private blogService: BlogService,
              private authService: AuthService,
              private jwtHelperService: JwtHelperService,
              private userService: UserService,
              private tutorService: TutorService,
              private studentService: StudentService,
              private adminService: AdminserviceService,
              private formBuilder: FormBuilder,
              private bloglikesService: BloglikesService,  
              private districtService: DistrictService,
              private commentService: CommentService,
              private gradeService: GradeService,
              private majorService: MajorService,
              private majorgradeService: MajorgradeService,
              private majorgradetutorService: MajorgradetutorService
              
  ) { }

  ngOnInit(): void {
    this.loadAllData();


    this.userFormUpdate = this.formBuilder.group({
      nameUpdate: ['', [Validators.required]],
      birthdayUpdate: ['', [Validators.required]],
      districtUpdate: ['', [Validators.required]]
    });

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
      this.gradesLeft = this.grades.filter(grade => grade.id! >= 1 && grade.id! <= 6);
      this.gradesRight = this.grades.filter(grade => grade.id! >= 7 && grade.id! <= 12);
      this.setUserHold()
      this.findMyBlogs()
      this.listMajorGradeTutorByTutorId()
      this.setLikeNumberOfBlogs()
    });
    
  }


  listMajorGradeTutorByTutorId(){
    this.majorgradetutorService.getAllMajorgradeTutorDtoByTutorId(this.userHold.idStudentOrTutor).subscribe(data => {
      this.myMajorgradetutor = data
      this.findMajorByTutorId()

    });
  }

  myMajorGradeList: Majorgrade[] = [];
  myMajorGradeListName: { name?: string; grade?: string }[] = [];
  findMajorByTutorId(){
    for (const majorgradetutor of this.myMajorgradetutor) {
      const majorGrade = this.majorGrades.find(majorGrade => majorGrade.id === majorgradetutor.majorGradeId)!
      const majorName =  this.majors.find(major => major.id === majorGrade?.majorId)?.name
      const gradeName =  this.grades.find(grade => grade.id === majorGrade?.gradeId)?.name
      this.myMajorGradeListName.push({name: majorName, grade: gradeName });
      this.myMajorGradeList.push(majorGrade)
      this.mySaveMajorgradetutor.push(new Majorgradetutor(null,majorGrade.id!,this.userHold.idStudentOrTutor))
    }
    console.log(this.myMajorGradeListName)
  }

  findMyBlogs(){
    this.myBlogs = this.blogs.filter(blog => blog.userDto?.id === this.userHold.idUser && blog.status === 'APPROVED')
  }

  findObjectByUser(user: User):any{
    if(user.role === 'STUDENT'){
     return this.students.filter(student => student.username === user.username)[0]
    }
    if(user.role === 'TUTOR'){
      return this.tutors.filter(tutor => tutor.username === user.username)[0]
    }
  }

  findObjectByUserId(studentOrTutorId: Number):any{
    if(this.userHold.role === 'STUDENT'){
      return this.students.filter(student => student.id === studentOrTutorId)[0]
     }
    if(this.userHold.role === 'TUTOR'){
       return this.tutors.filter(tutor => tutor.id === studentOrTutorId)[0]
     }
     if(this.userHold.role === 'ADMIN'){
      return this.admins.filter(admin => admin.id === studentOrTutorId)[0]
    }
  }

  findDistrictById(id: Number):any{
    return this.districts.filter(district => district.id === id)[0]
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
      console.log(this.userHold.imageFile)
    }
    if(this.userHold.role === 'ADMIN'){
      const userTutorOrStudent = this.admins.filter(admin => admin.username === this.userHold.username)[0]
      this.userHold.idStudentOrTutor = userTutorOrStudent.id!
      this.userHold.imageFile = userTutorOrStudent.imageFile ?? null
      console.log(this.userHold.imageFile)
    }
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

  saveBlog(){
    const user = this.users.filter(user => user.username === this.userHold.username)
      if(this.content === ''){
        alert('Please enter something')
        return
      }
      const blogFormData = new FormData();
      blogFormData.append('content', this.content);
      blogFormData.append('userId', user[0].id! +'');
      blogFormData.append('file', this.selectedFile!);

      if(this.userHold.role === 'ADMIN'){
        blogFormData.append('status', 'APPROVED');
      }

      blogFormData.forEach((value, key) => {
        console.log(key, value);
      });
      this.blogService.saveBlog(blogFormData).subscribe(
        data =>{
          console.log(data)
          this.resetFormAndReload()
        }
      ) 
  }

  resetFormAndReload() {
    this.content = '';
    this.selectedFile = null;
    this.fileInputNew!.nativeElement.value = '';
    this.loadAllData()

  }


  closeModalUpdateUser(){
    if (this.myModalUpdateUser) {
      this.myModalUpdateUser.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalChooseMajor() {
      const modalElement = document.getElementById('myModalChooseMajor');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }


  closeModalChooseMajor(){
    if (this.modalChooseMajor) {
      this.modalChooseMajor.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalUpdateUser() {
      const modalElement = document.getElementById('myModalUpdateUser');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  closeModalUpdate(){
    if (this.myModalUpdateBlog) {
      this.myModalUpdateBlog.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
    this.loadAllData()
    this.selectedFile = null
    this.fileInput.nativeElement.value = ''; // reset input file


  }

  openModalUpdate() {
      const modalElement = document.getElementById('myModalUpdateBlog');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  setDataForUpdateBlog(blog: Blog){
    this.blogUpdate = blog
    this.openModalUpdate();
  }


  
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && file.size > 30 * 1024 * 1024) { //30MB
      alert("File quá lớn! Giới hạn là 30MB.");
      return;
    }
    this.selectedFile = file;
    this.blogUpdate!.fileName = file.name;
  }

  onFileSelectedImage(event: any) {
    const file: File = event.target.files[0];
    if (file && file.size > 30 * 1024 * 1024) { // 30MB
      alert("File quá lớn! Giới hạn là 30MB.");
      return;
    }
    this.selectedImageFile = file;
  }

  removeFile(){
    this.blogUpdate!.fileName = '';
    this.fileInput.nativeElement.value = ''
  }

  onSubmitUpdateBlog(){
    if(this.blogUpdate?.content === ''){
      alert('Please enter something')
      return;
    }else{
      const blogFormDataUpdate = new FormData();
      blogFormDataUpdate.append('content', this.blogUpdate!.content!);
      blogFormDataUpdate.append('fileName', this.blogUpdate!.fileName!);
      blogFormDataUpdate.append('file', this.selectedFile!);

      blogFormDataUpdate.forEach((value, key) => {
        console.log(key, value);
      });

      this.blogService.updateBlog(blogFormDataUpdate,this.blogUpdate!.id!).subscribe(
        data =>{
          console.log(data);
          this.loadAllData();
          this.closeModalUpdate();
        }
      ) 
    }

  }

  onSubmitUpdateUser(){
    if(this.userFormUpdate.valid == false){
      alert('Please enter enough')
    }else{
      const definedUserFormData = new FormData();
      definedUserFormData.append('id', this.userHold.idStudentOrTutor! + "");
      definedUserFormData.append('username', this.userHold.username!);
      definedUserFormData.append('name', this.userFormUpdate.value.nameUpdate);
      definedUserFormData.append('birthday', this.userFormUpdate.value.birthdayUpdate);
      definedUserFormData.append('imageFile', this.userHold.imageFile!);
      definedUserFormData.append('majorDtoId', this.userFormUpdate.value.districtUpdate);
      definedUserFormData.append('file', this.selectedImageFile!);

      if(this.userHold.role === 'STUDENT'){
        this.studentService.updateStudent(definedUserFormData,this.userHold.idStudentOrTutor!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }
      if(this.userHold.role === 'TUTOR'){
        this.tutorService.updateTutor(definedUserFormData,this.userHold.idStudentOrTutor!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }
      if(this.userHold.role === 'ADMIN'){
        this.adminService.updateAdmin(definedUserFormData,this.userHold.idStudentOrTutor!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }


      this.closeModalUpdateUser();  
    }
  }

  updateUser() {
    this.userFormUpdate.patchValue  ({
      nameUpdate: this.userHold.username,
      birthdayUpdate: this.findObjectByUserId(this.userHold.idStudentOrTutor).birthday,
      districtUpdate: this.userHold.idDistrict
    });
    this.openModalUpdateUser()
  }

  setLikeNumberOfBlogs(){
    for (let blog of this.blogs) {
      this.loadLikeCount(blog.id!)
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

  holdContentComment: string = ''
  holdIdComment: number = 0;
  holdBlogIdComment: number = 0;
  showUpdateCommentModal(comment: Commentblog){
    this.holdContentComment = comment.content!
    this.holdIdComment = comment.id!
    this.holdBlogIdComment = comment.blogId!
    this.openModalUpdateComment()
  }

  closeModalUpdateComment(){
    if (this.modalUpdateComment) {
      this.modalUpdateComment.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalUpdateComment() {
      const modalElement = document.getElementById('myModalUpdateComment');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  deleteComment(comment: Commentblog){
    this.commentService.deleteComment(comment.id!).subscribe(data => {
      this.showComment(comment.blogId!)
    });
  }

  updateComment(){
    if(this.holdContentComment === null){
      alert('Please enter something')
      return
    }

    const newComment = new Commentblog(null,this.holdContentComment!,null,null,null)
    this.commentService.updateComment(newComment,this.holdIdComment).subscribe(data => {
      this.closeModalUpdateComment()
      this.showComment(this.holdBlogIdComment)
    });
  }


  findMajorByGrade(gradeId: number){
    const listMajorGradeByGradeId = this.majorGrades.filter(majorGrade => majorGrade.gradeId === gradeId)
    const majorOfGrade = [];
    for (const majorGradeByGradeId of listMajorGradeByGradeId) {
      const majorByGradeId = this.majors.find(major => major.id === majorGradeByGradeId.majorId)
      majorOfGrade.push(majorByGradeId);
    }
    return majorOfGrade;
  }

  mySaveMajorgradetutor: Majorgradetutor[] = [];
  mySetDeleteMajorgradetutor: Set<Majorgradetutor> = new Set();

  onMajorCheckboxChange(event: any, majorId: number, gradeId: number) {
    const majorGradeHold = this.majorGrades.filter(majorGrade => majorGrade.gradeId === gradeId && majorGrade.majorId === majorId)[0] 
    console.log(majorGradeHold.id + 'sad' );

    if (event.target.checked) {
      console.log('Checked majorGrade id:', majorId, gradeId );
      this.mySaveMajorgradetutor.push(new Majorgradetutor(null,majorGradeHold.id!,this.userHold.idStudentOrTutor) );
      // this.mySetDeleteMajorgradetutor.delete(this.mySaveMajorgradetutor.find(majorgradetutor => majorgradetutor.majorGradeId === majorGradeHold.id)!);
      this.deleteMajorGradeTutorByVaule(this.mySetDeleteMajorgradetutor,this.userHold.idStudentOrTutor,majorGradeHold.id!)
      console.log(this.mySetDeleteMajorgradetutor)
    } else {
      console.log('Unchecked major id:', majorId, gradeId);
      this.mySetDeleteMajorgradetutor.add(this.mySaveMajorgradetutor.find(majorgradetutor => majorgradetutor.majorGradeId === majorGradeHold.id)!)
      this.mySaveMajorgradetutor = this.mySaveMajorgradetutor.filter(majorgradetutor => majorgradetutor.majorGradeId !== majorGradeHold.id);
      console.log(this.mySetDeleteMajorgradetutor)
    }
  }
  
  saveSelectedMajors(){

    this.majorgradetutorService.saveMajorGradeTutor(this.mySaveMajorgradetutor).subscribe(
      data => {
        console.log(data)
        this.closeModalChooseMajor()
        this.loadAllData()
    });

    this.majorgradetutorService.deleteMajorGradeTutor(this.mySetDeleteMajorgradetutor).subscribe(
      data => {
        console.log('delete successfully')
        this.closeModalChooseMajor()
        this.loadAllData()
    });


  }

  isMajorChecked(majorId: number,gradeId: number): boolean {
    const check = this.myMajorGradeList.some(obj => obj.majorId === majorId && obj.gradeId === gradeId);
    return check;
  }


  deleteMajorGradeTutorByVaule(set: Set<Majorgradetutor>, tutorId: number, majorGradeId: number){
    console.log(majorGradeId)
    console.log(tutorId)

    for (const item of set) { 
      if (item.majorGradeId === majorGradeId && item.tutorId === tutorId) {
        set.delete(item);
        break;
      }
    }

  }

}
