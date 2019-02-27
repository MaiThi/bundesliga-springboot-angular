import { Component, OnInit } from '@angular/core';
import {AuthService} from '../ApiService/auth-login/auth.service';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';
import {MatchDetail} from '../Model/match-detail';
import {MatchDetailsService} from '../ApiService/match-details.service';
import {MatchService} from '../ApiService/match.service';
import {Match} from '../Model/match';

@Component({
  selector: 'app-test-authority',
  templateUrl: './test-authority.component.html',
  styleUrls: ['./test-authority.component.css']
})
export class TestAuthorityComponent implements OnInit {

  matchId: number;
  homeTeamId: number;
  visitTeamId: number;
  match: Match;
  matchDetails: MatchDetail[] = [];
  constructor(private matchDetailService: MatchDetailsService,
              private matchService: MatchService) { }

  ngOnInit() {
    this.matchId = 17;
    this.matchService.getMatchById(this.matchId).subscribe(
      res1 => {
        this.match = res1;
        this.homeTeamId = this.match.homeTeamId;
        this.visitTeamId = this.match.visitTeamId;
        this.matchDetailService.getDetailsByMatchId(17).subscribe(
          res => {
            this.matchDetails = res;
          },
          error1 => {
            alert('Getting match details has occured erros');
          }
        );
      },
      error2 => {}
  );
  }

}

