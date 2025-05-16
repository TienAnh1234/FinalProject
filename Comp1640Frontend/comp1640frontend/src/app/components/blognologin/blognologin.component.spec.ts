import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlognologinComponent } from './blognologin.component';

describe('BlognologinComponent', () => {
  let component: BlognologinComponent;
  let fixture: ComponentFixture<BlognologinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlognologinComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BlognologinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
