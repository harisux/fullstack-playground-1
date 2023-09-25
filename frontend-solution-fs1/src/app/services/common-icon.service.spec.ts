import { TestBed } from '@angular/core/testing';

import { CommonIconService } from './common-icon.service';

describe('CommonIconService', () => {
  let service: CommonIconService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommonIconService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
