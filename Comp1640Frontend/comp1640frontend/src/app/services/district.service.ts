import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'https://provinces.open-api.vn/api';


@Injectable({
  providedIn: 'root'
})


export class DistrictService {

  private districtUrl = 'http://localhost:8080/api/district';



  constructor(private HttpClient: HttpClient) { }

  getDistrictList(): Observable<any> {
    return this.HttpClient.get<any>(this.districtUrl);
  }

  
}
