import { TestBed } from '@angular/core/testing';

import { PlayerSeasonService } from './player-season.service';

describe('PlayerSeasonService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PlayerSeasonService = TestBed.get(PlayerSeasonService);
    expect(service).toBeTruthy();
  });
});
