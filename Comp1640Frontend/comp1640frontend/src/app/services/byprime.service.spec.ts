import { TestBed } from '@angular/core/testing';

import { ByprimeService } from './byprime.service';

describe('ByprimeService', () => {
  let service: ByprimeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ByprimeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
