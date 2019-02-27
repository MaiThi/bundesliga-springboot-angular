import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Match} from '../../Model/match';
import {MatchService} from '../../ApiService/match.service';
import {NotificationService} from '../../ApiService/notification.service';

@Component({
  selector: 'app-three-step-for-match-detail',
  templateUrl: './three-step-for-match-detail.component.html',
  styleUrls: ['./three-step-for-match-detail.component.css']
})
export class ThreeStepForMatchDetailComponent implements OnInit {

  matchId: number;
  match: Match;
  homeTeamId: number;
  visitTeamId: number;
  constructor(private route: ActivatedRoute,
              private matchService: MatchService,
              private notificationService: NotificationService) { }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.matchId = id;
    this.matchService.getMatchById(this.matchId).subscribe(
      res => {
        this.match = res;
       // this.notificationService.success(':: Getting info successfully');
      },
      error1 => {
        this.notificationService.warn(':: Getting info unsuccessfully');
      }
    );
  }

  calculateScoreForTeam() {
    this.matchService.calculateScoreForTeams(this.matchId).subscribe(
      res => {
        this.notificationService.success(':: Calculate score successfully');
      },
      error1 => {
        this.notificationService.warn(':: Calculate score unsuccessfully');
      }
    );
  }
}
