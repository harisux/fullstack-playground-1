import { TestBed } from '@angular/core/testing';

import { BackendDiscoveryService } from './backend-discovery.service';

describe('BackendDiscoveryService', () => {
  let service: BackendDiscoveryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BackendDiscoveryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
