import { TestBed } from '@angular/core/testing';

import { MatchDetailsService } from './match-details.service';

describe('MatchDetailsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MatchDetailsService = TestBed.get(MatchDetailsService);
    expect(service).toBeTruthy();
  });
});
