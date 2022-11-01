import { TestBed } from '@angular/core/testing';

import { SchnitzelService } from './schnitzel.service';

describe('SchnitzelService', () => {
  let service: SchnitzelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchnitzelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
