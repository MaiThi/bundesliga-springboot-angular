import { TestBed } from '@angular/core/testing';

import { PlayerInMatchService } from './player-in-match.service';

describe('PlayerInMatchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PlayerInMatchService = TestBed.get(PlayerInMatchService);
    expect(service).toBeTruthy();
  });
});
