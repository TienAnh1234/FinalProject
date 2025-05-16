import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Admin } from 'src/app/common/admin';
import { Blog } from 'src/app/common/blog';
import { Classroom } from 'src/app/common/classroom';
import { Commentblog } from 'src/app/common/commentblog';
import { Friendrequest } from 'src/app/common/friendrequest';
import { Individualpayment } from 'src/app/common/individualpayment';
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
import { PaymentService } from 'src/app/services/payment.service';
import { ScheduleService } from 'src/app/services/schedule.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';
@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  @ViewChild('myModalUpdateUser') modalUpdateUser: ElementRef | undefined;
  @ViewChild('myModalBookSchedule') modalBookSchedule: ElementRef | undefined;
  @ViewChild('myModalChatting') modalChatting: ElementRef | undefined;
  @ViewChild('myModalUpdateComment') modalUpdateComment: ElementRef | undefined;
  @ViewChild('fileInput') fileInput!: ElementRef| undefined;



  showNotification = false;
  showListSchedule = false;
  showListStudentsOfTutor = false;
  showListStudentsOfTutorOutSide = false;
  selectedFile: File | null = null;


  token: string = '';

  userHold = {username:'',role:'',name:'',birthday:new Date(),imageFile:'',id:0,idStudentOfTutor:0,status:''};
  admins: Admin[] = []
  students: Student[] =[];
  studentsOfTutor: Student[] =[];
  tutorsOfStudent: Tutor[] =[];
  commentBlogsAll: Commentblog[] = [];
  commentBlogsOfEachBlog: Commentblog[] = [];

  addFriendRequestsIReceived: Friendrequest[] = [];

  wbschatmessages: Wbschatmessage[] = [];
  recipientName: string = '';
  recipientId!: number
  message: string = '';
  
  newComment: string = ''

  statusSchedule: string = ''

  tutors: Tutor[] =[];
  users: User[] =[];
  majors: Major[] =[];
  classrooms: Classroom[] = []
  schedulesOfTutor: Schedule[] = []
  payments: Individualpayment[] = []

  blogs: Blog[] =[];

  content: string = '';  
  
  majorOfUser: Major | undefined;


  userFormUpdate!: FormGroup;
  scheduleForm!: FormGroup;

  minDateTime: string = '';
  // minEndTime: string = '';
  // maxEndTime: string = '';

  fixedTimeSlots = [
    { start: '07:10', end: '08:40' },
    { start: '08:50', end: '10:20' },
    { start: '10:30', end: '12:00' },
    { start: '12:50', end: '14:20' },
    { start: '14:30', end: '16:00' }
  ];


  openByPrimeComponent(){
    this.router.navigate(['/user/byprime']); 
  }

  openScheduleComponent(){
    this.router.navigate(['/user/schedule']); 
  }

  openMyBlogComponent(){
    this.router.navigate(['/user/myblog']); 
  }

  constructor(private jwtHelperService: JwtHelperService,
              private authService: AuthService,
              private studentService: StudentService,
              private tutorService: TutorService,
              private adminService: AdminserviceService,
              private blogService: BlogService,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private majorService: MajorService,
              private scheduleService: ScheduleService,
              private classroomService: ClassroomService,
              private websocketService: WebsocketService, 
              private router: Router,
              private bloglikesService: BloglikesService,
              private commentService: CommentService,
              private paymentService: PaymentService
              
  ) { }
  newMessage: Wbschatmessage[] = [];

  ngOnInit(): void {
    this.userFormUpdate = this.formBuilder.group({
      nameUpdate: ['', [Validators.required, Validators.minLength(3)]],
      birthdayUpdate: ['', [Validators.required, Validators.minLength(3)]],
      majorUpdate: ['', [Validators.required]]
    });

    this.scheduleForm = this.formBuilder.group({
      studentDtoId: ['', [Validators.required]],
      startTime: ['', [Validators.required]],
      startDay: ['', [Validators.required]]

    });


    this.token = this.authService.getToken()!;
    this.userHold = this.jwtHelperService.getUserFromToken(this.token)

    this.loadAllData()

    this.websocketService.messageSubject.subscribe(wbschatmessage => {
      try {
          this.wbschatmessages.push(wbschatmessage);
          this.newMessage.push(wbschatmessage)
      } catch (error) {
          console.log(error)
      }
      
    });

    this.websocketService.friendRequestSubject.subscribe(data => {
      try {
          this.addFriendRequestsIReceived.push(data);
      } catch (error) {
          console.log(error)
      }
      
    });

    this.websocketService.acceptFriendSubject.subscribe(data => {
      this.loadAllData()
    });

  }


  setRecipientMessage(recipient: string){
    this.recipientName = recipient;
    this.wbschatmessages = [];
    if(this.recipientName != ""){
      for (let i = 0; i < this.users.length; i++) {
        if (this.users[i].username === this.recipientName) {
          this.recipientId = this.users[i].id!;
          break;
        }
      }
      const user = this.users.filter(user => user.username === this.userHold.username)

      this.websocketService.getOldMessages(user[0].id!, this.recipientId ).subscribe(data => {

        this.wbschatmessages = data;
        // for (let i = 0; i < this.wbschatmessages.length; i++) {
        //     console.log(i +' ' + this.wbschatmessages[i].sender +' ' + this.wbschatmessages[i].recipient )
        //   }
      });

      if(this.recipientId){
        this.openModalChatting();
        return
      }
      
    }

  }



  sendMessage(){
    this.newMessage = []
    const wbschatmessage = new Wbschatmessage(this.userHold.username,this.recipientName, this.message)
    this.wbschatmessages.push(wbschatmessage);
    this.websocketService.sendMessage(this.userHold.username, this.recipientName, this.message);
    this.message ='';
  }


  myAddFriendRequest(){
    const recipientName = this.userHold.username
    let recipientId = 0;
    console.log(recipientName)    


    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].username === recipientName) {
        recipientId = this.users[i].id!;      

        break;
      }
    }

    this.websocketService.getAddFriendRequestIReceived(recipientId).subscribe(
      data =>{
        this.addFriendRequestsIReceived = data
      })
  }

  sendAddFriendRequest(sender:string, recipient:string){
    this.websocketService.sendAddFriendRequest(sender,recipient)
  }





  loadAllData() {
    forkJoin({
      tutors: this.tutorService.getTutorList(),          // Lấy tutors
      students: this.studentService.getStudentList(),    // Lấy students
      blogs: this.blogService.getBlogList(),
      users: this.userService.getUserList(),
      majors: this.majorService.getMajorList(),
      admins: this.adminService.getAdminList(),
      classrooms: this.classroomService.getClassroomList(),
      payments: this.paymentService.getPaymentList()
    }).subscribe(({ tutors, students, users, blogs, majors, classrooms, admins, payments  }) => {
      this.tutors = tutors;
      this.students = students;
      this.blogs = blogs
      this.getApprovedBlogs();
      this.users = users
      this.majors = majors
      this.admins = admins
      this.payments = payments
      this.getInforOfUser();

      this.classrooms = classrooms

      this.listAllComments()      
      this.getScheduleUserList();
      this.setLikeNumberOfBlogs();
      this.myAddFriendRequest()
      if(this.userHold.role === 'TUTOR'){
        this.listStudentsOfTutor();
      }
      if(this.userHold.role === 'STUDENT'){
        this.listTutorsOfStudent();
      }
      
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


  closeModalBookSchedule(){
    if (this.modalBookSchedule) {
      this.modalBookSchedule.nativeElement.style.display = 'none';
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

  closeModalUpdate(){
    if (this.modalUpdateUser) {
      this.modalUpdateUser.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalUpdate() {
      const modalElement = document.getElementById('myModalUpdateUser');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
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


  
  closeModalChatting(){
    if (this.modalChatting) {
      this.modalChatting.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalChatting() {
      const modalElement = document.getElementById('myModalChatting');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && file.size > 30 * 1024 * 1024) { // 30MB
      alert("File quá lớn! Giới hạn là 30MB.");
      console.log(file.size)
      return;
    }
    console.log(file.size)

    this.selectedFile = file;
  }

  onSubmitUpdate(){
    
    if(this.userFormUpdate.valid == false){
      this.showNotification = true;
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      console.log('Form không hợp lệ!', this.userFormUpdate.controls);
    }else{
      const definedUserFormData = new FormData();
      definedUserFormData.append('id', this.userHold.id! + "");
      
      definedUserFormData.append('name', this.userFormUpdate.value.nameUpdate);
      definedUserFormData.append('birthday', this.userFormUpdate.value.birthdayUpdate);
      definedUserFormData.append('imageFile', this.userHold.imageFile!);
      definedUserFormData.append('username', this.userHold.username!);
      definedUserFormData.append('majorDtoId', this.userFormUpdate.value.majorUpdate);
      definedUserFormData.append('file', this.selectedFile!);

      if(this.userHold.role === 'STUDENT'){
        this.studentService.updateStudent(definedUserFormData,this.userHold.id!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }else{
        this.tutorService.updateTutor(definedUserFormData,this.userHold.id!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }

      this.closeModalUpdate();  
    }
  }

    
  getInforOfUser(){
    if(this.userHold.role === 'STUDENT'){
      const student = this.students.filter(student => student.username === this.userHold.username)
      this.userHold.name = student[0].name!
      this.userHold.birthday = student[0].birthday!
      this.userHold.imageFile = student[0].imageFile!
      this.userHold.id = student[0].id!
      this.majorOfUser = student[0].majorDto
      this.userHold.idStudentOfTutor =student[0].id!
    }else{
      const tutor = this.tutors.filter(tutor => tutor.username === this.userHold.username)
      this.userHold.name = tutor[0].name!
      this.userHold.birthday = tutor[0].birthday!
      this.userHold.imageFile = tutor[0].imageFile!
      this.userHold.id = tutor[0].id!
      this.majorOfUser = tutor[0].majorDto
      this.userHold.idStudentOfTutor =tutor[0].id!
      this.userHold.status = tutor[0].status!

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
    this.fileInput!.nativeElement.value = '';
    alert('Your blog is in queue')
    this.loadAllData()

  }
  
  findPaymentByTutorId(tutorId: number){
    return this.payments.filter(payment => payment.tutorId === tutorId)[0]
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

  updateUser() {
    this.userFormUpdate.patchValue  ({
      nameUpdate: this.userHold.name,
      birthdayUpdate: this.userHold.birthday,
      majorUpdate: this.majorOfUser?.id
    });
    this.openModalUpdate()
  }

  bookSchedule() {

    this.studentsOfTutor = []
    const classroomOfTutor = this.classrooms.filter(classroom => classroom.tutorId === this.userHold.id)
    if(classroomOfTutor.length == 0){
      this.showListStudentsOfTutor = false;
    }else{
      this.showListStudentsOfTutor = true;
      for (let i = 0; i < classroomOfTutor[0].studentsId.length; i++) {
        this.studentsOfTutor.push(this.students.filter(student => student.id === classroomOfTutor[0].studentsId[i])[0])
        console.log(this.studentsOfTutor[i])
      }
      
    }
    this.openModalBookSchedule();
  }



  getScheduleUserList(){
    this.scheduleService.getScheduleList(this.userHold.id).subscribe(
      data =>{
        if(data == null){
          this.schedulesOfTutor = []
        }else{
          this.schedulesOfTutor = data
          console.log(data)
        }
      }
    ) 
  }

  checkShowListSchedule(){
    if(this.showListSchedule == true){
      this.showListSchedule = false;
    }else{
      this.showListSchedule = true;
      console.log(this.userHold.id)
      this.getScheduleUserList()
    }
  }
  

  updateStatusSchedule(schedule: Schedule){
    if(schedule.status === ''){
      alert('Set status successfully')
      this.getScheduleUserList();
    }else{
      console.log(schedule.status)
      this.scheduleService.updateSchedule(schedule, schedule.id!).subscribe(
        data =>{
          console.log(data)
          this.getScheduleUserList();
          alert('Set status successfully')
        }
      ) 
    }
  }


  deteteSchedule(schedule: Schedule){
    console.log(schedule)
    if (confirm('Do you want to delete this schedule?')) {
      this.scheduleService.deleteSchedule(schedule.id!).subscribe(
        {
          next: () => {
            this.getScheduleUserList()
            alert('Delete schedule successfully')
          },
          error: (error) => {
            console.error('Delete failedly:', error);
          }
        }
      ) 

      
    }
  }

  listStudentsOfTutor(){
    console.log(this.userHold.username)
    this.studentsOfTutor = []
    const classroomOfTutor = this.classrooms.filter(classroom => classroom.tutorId === this.userHold.id)
    console.log(this.classrooms)

    console.log(classroomOfTutor)

    for (let i = 0; i < classroomOfTutor[0].studentsId.length; i++) {
      this.studentsOfTutor.push(this.students.filter(student => student.id === classroomOfTutor[0].studentsId[i])[0])
      console.log(this.studentsOfTutor[i])
    }
  }

  listTutorsOfStudent(){
    console.log(this.userHold.username)

    this.tutorsOfStudent = []
    const result = this.classrooms.filter(classroom => classroom.studentsId.includes(this.userHold.id));
    console.log(this.classrooms)

    console.log(result)
    for (let i = 0; i < result.length; i++) {
      this.tutorsOfStudent.push(this.tutors.filter(tutor => tutor.id === result[i].tutorId)[0])
      console.log(this.tutorsOfStudent[i])
    }
  }

  checkShowListStudentOrTutorOutSide(){
    if(this.showListStudentsOfTutorOutSide == true){
      this.showListStudentsOfTutorOutSide = false;
    }else{
      this.showListStudentsOfTutorOutSide = true;
      if(this.userHold.role == "TUTOR"){
        this.listStudentsOfTutor()
      }else{
        this.listTutorsOfStudent()
      }

      
    }
  }

  setLikeNumberOfBlogs(){
    for (let blog of this.blogs) {
      this.loadLikeCount(blog.id!)
    }
  }


  toggleLike(blogId: number) {
    const user = this.users.filter(user => user.username === this.userHold.username)
    const userId = user[0].id!; 
  
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

  findUsernameById(id: number): string {
    const user = this.users.find(u => u.id === id);
    return user?.username!;
  }

  findObjectByUsername(username: string){
    const user = this.users.filter(user => user.username === username)[0]

    if(user.role === 'STUDENT'){
      return this.students.filter(student => student.username === user.username)[0]
     }else{
       return this.tutors.filter(tutor => tutor.username === user.username)[0]
     }
  }

  findUserByUsername(username: string){
    return this.users.filter(user => user.username === username)[0]

  }
  

  handleInput(event: Event) {
    this.newComment = (event.target as HTMLInputElement).value;
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
  
  holdContentComment: string = ''
  holdIdComment: number = 0;
  holdBlogIdComment: number = 0;
  showUpdateCommentModal(comment: Commentblog){
    this.holdContentComment = comment.content!
    this.holdIdComment = comment.id!
    this.holdBlogIdComment = comment.blogId!
    this.openModalUpdateComment()
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

  deleteComment(comment: Commentblog){
    this.commentService.deleteComment(comment.id!).subscribe(data => {
      this.showComment(comment.blogId!)
    });
  }


  logoutUser(){
    this.authService.logout()
  }

  checkThisGuy(user: User){
    this.router.navigate(['user/searchblog'], { state: { data: user } });
  }

  findUser(){
    this.router.navigate(['user/searchuser']);
  }

  
  showRequests: boolean = false;
  pendingRequests = [
    // Dữ liệu mẫu, bạn thay bằng dữ liệu từ backend
    { id: 1, senderName: 'Nguyễn Văn A' },
    { id: 2, senderName: 'Trần Thị B' }
  ];

  acceptRequest(request: Friendrequest) {
    request.status = 'APPROVED'
    this.addFriendRequestsIReceived = this.addFriendRequestsIReceived.filter(friendRequest => friendRequest.recipient !== request.recipient)

    this.websocketService.sendAcceptRequest(request)

    setTimeout(() => {
      // Các lệnh chạy sau 400ms
      this.loadAllData();
    }, 400); // 400ms


    // window.location.reload();

    // this.classroomService.saveClassroomMain(request).subscribe(
    //   data => {
    //     console.log('Accepted', data);
    //     this.myAddFriendRequest();
    //   })
  }

  declineRequest(request: Friendrequest) {
    request.status = 'DECLINED'
    this.addFriendRequestsIReceived = this.addFriendRequestsIReceived.filter(friendRequest => friendRequest.recipient !== request.recipient)
    this.websocketService.sendAcceptRequest(request)
    
    setTimeout(() => {
      // Các lệnh chạy sau 400ms
      this.loadAllData();
    }, 400); // 400ms

    // this.classroomService.deleteFriendRequest(request.id).subscribe(
    //   data=>{
    //     this.myAddFriendRequest();
    //     console.log('Denied');

    // })
  }


  deleteClassroomByStudentIdAndTutorId(studentOrTutorId: number){
    if(this.userHold.role === 'TUTOR'){
      this.classroomService.deleteClassByStudentIdAndTutorId(this.userHold.idStudentOfTutor,studentOrTutorId).subscribe(
        data => {
          this.loadAllData()
        }
      )
    }else{
      this.classroomService.deleteClassByStudentIdAndTutorId(studentOrTutorId,this.userHold.idStudentOfTutor).subscribe(
        data => {
          window.location.reload()
        }
      )
    }

  }

}





