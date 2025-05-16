import { TestBed } from '@angular/core/testing';

import { MajorgradetutorService } from './majorgradetutor.service';

describe('MajorgradetutorService', () => {
  let service: MajorgradetutorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MajorgradetutorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
