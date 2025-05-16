import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ByprimeComponent } from './byprime.component';

describe('ByprimeComponent', () => {
  let component: ByprimeComponent;
  let fixture: ComponentFixture<ByprimeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ByprimeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ByprimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
