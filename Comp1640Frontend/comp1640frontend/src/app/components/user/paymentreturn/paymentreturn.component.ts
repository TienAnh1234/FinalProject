import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-paymentreturn',
  templateUrl: './paymentreturn.component.html',
  styleUrls: ['./paymentreturn.component.css']
})
export class PaymentreturnComponent implements OnInit {

  result: any = null;
  error = false;

  constructor(private route: ActivatedRoute,             
    private router: Router,
    private http: HttpClient) {}

  ngOnInit(): void {
    console.log(1231312)
    this.route.queryParams.subscribe(params => {
      this.result = params
      console.log(this.result)
      });
  }

  backToMain(){
    this.router.navigate(['user/blog']);
  }

}
