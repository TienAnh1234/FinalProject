import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchblogComponent } from './searchblog.component';

describe('SearchblogComponent', () => {
  let component: SearchblogComponent;
  let fixture: ComponentFixture<SearchblogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchblogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchblogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
