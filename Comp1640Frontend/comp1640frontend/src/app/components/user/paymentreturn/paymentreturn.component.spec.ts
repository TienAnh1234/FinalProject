import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentreturnComponent } from './paymentreturn.component';

describe('PaymentreturnComponent', () => {
  let component: PaymentreturnComponent;
  let fixture: ComponentFixture<PaymentreturnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentreturnComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentreturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
