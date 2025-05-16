import { TestBed } from '@angular/core/testing';

import { BloglikesService } from './bloglikes.service';

describe('BloglikesService', () => {
  let service: BloglikesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BloglikesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
