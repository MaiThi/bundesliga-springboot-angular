import { Component, OnInit } from '@angular/core';
import {Match} from '../Model/match';
import {MatchDetail} from '../Model/match-detail';
import {MatchDetailsService} from '../ApiService/match-details.service';
import {MatchService} from '../ApiService/match.service';
import {ActivatedRoute, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-show-match-info-annonymous',
  templateUrl: './show-match-info-annonymous.component.html',
  styleUrls: ['./show-match-info-annonymous.component.css']
})
export class ShowMatchInfoAnnonymousComponent implements OnInit {
  matchId: number;
  homeTeamId: number;
  visitTeamId: number;
  match: Match;
  image: string;
  matchDetails: MatchDetail[] = [];
  constructor(private matchDetailService: MatchDetailsService,
              private route: ActivatedRoute,
              private matchService: MatchService) { }
  formatsDateTest: string[] = [
    'dd/MM/yyyy',
    'EEE,  dd/MM/yyyy | hh:mm',
    'dd-MM-yyyy',
    'EEE,  dd-MM-yyyy | HH:mm',
    'MM/dd/yyyy',
    'EEE, MM/dd/yyyy | hh:mm',
    'yyyy/MM/dd',
    'EEE, yyyy/MM/dd | HH:mm',
    'dd/MM/yy',
    'EEE, dd/MM/yy | hh:mm',
    'HH:mm',
  ];

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.matchId = id;
    this.matchService.getMatchById(this.matchId).subscribe(
      res1 => {
        this.match = res1;
        this.homeTeamId = this.match.homeTeamId;
        this.visitTeamId = this.match.visitTeamId;
        this.matchDetailService.getDetailsByMatchId(this.matchId).subscribe(
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
