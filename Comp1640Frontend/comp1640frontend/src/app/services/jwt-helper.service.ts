import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtHelperService {

  constructor() { }

    // ✅ Hàm giải mã JWT Token
    decodeToken(token: string): any {
      if (!token) return null;
      try {
        const payload = token.split('.')[1]; // Lấy phần payload
        return JSON.parse(atob(payload)); // Giải mã từ Base64
      } catch (error) {
        console.error('Lỗi giải mã token:', error);
        return null;
      }
    }

      // ✅ Hàm kiểm tra token đã hết hạn
  isTokenExpired(token: string): boolean {
    const decoded = this.decodeToken(token);
    if (!decoded || !decoded.exp) return true;
    const expiryTime = decoded.exp * 1000; // Chuyển sang milliseconds
    return Date.now() > expiryTime;
  }

  getUserFromToken(token: string): any {
    const decoded = this.decodeToken(token);
    console.log(decoded.sub)
    console.log(decoded.role)

    if (decoded) {;
      return {
        username: decoded.sub || '',    // Lấy username
        role: decoded.role || '',            // Lấy role
      };
    }
    return null;
  }


}
