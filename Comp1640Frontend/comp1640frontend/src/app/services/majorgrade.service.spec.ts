import { TestBed } from '@angular/core/testing';

import { MajorgradeService } from './majorgrade.service';

describe('MajorgradeService', () => {
  let service: MajorgradeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MajorgradeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
