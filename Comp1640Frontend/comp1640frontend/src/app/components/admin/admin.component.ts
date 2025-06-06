import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router
  ) { }

  ngOnInit(): void {
    console.log("✅ AdminComponent Loaded");
  }

  logoutUser(){
    this.authService.logout()
  }

  openMyBlogComponent(){
    this.router.navigate(['/user/myblog']); 
  }

}
