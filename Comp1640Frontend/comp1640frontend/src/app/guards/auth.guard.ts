import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { JwtHelperService } from '../services/jwt-helper.service';
import { WebsocketService } from '../services/websocket.service';

@Injectable({
  providedIn: 'root'
})

@Injectable({
  providedIn: 'root' // ADDED providedIn root here.
})
export class AuthGuard implements CanActivate {


  canActivate(route: any): boolean {
    const token = this.authService.getToken();
    console.log(token)
    console.log('AuthGuard is running'); 
    

    if (!token || this.jwtHelper.isTokenExpired(token)) {
      console.log('loi 1'); 

      this.router.navigate(['/blognologin']); // Chuyển về trang đăng nhập
      return false;
    }

    const requiredRoles = route.data['role'];
    const userRole  = this.jwtHelper.getUserFromToken(token!).role;
    const username  = this.jwtHelper.getUserFromToken(token!).username;

    // console.log(userRole )
    // console.log(requiredRoles )
    // console.log(username )
    this.websocketService.connect(username);

    if  (!requiredRoles.includes(userRole)) {
      alert('You dont have  !');
      console.log('loi 2'); 

      this.router.navigate(['/blognologin']); // Chuyển về trang đăng nhập
      return false;
    }

    return true;
    
  }

  constructor(private authService: AuthService, private router: Router, private jwtHelper: JwtHelperService, private websocketService: WebsocketService) {}

  
}
