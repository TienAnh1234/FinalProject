import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveblogComponent } from './approveblog.component';

describe('ApproveblogComponent', () => {
  let component: ApproveblogComponent;
  let fixture: ComponentFixture<ApproveblogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApproveblogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApproveblogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
