import {Component, Input, OnInit} from '@angular/core';
import {Match} from '../../Model/match';
import {MatchService} from '../../ApiService/match.service';
import {MatchDetailsService} from '../../ApiService/match-details.service';
import {MatchDetail} from '../../Model/match-detail';

@Component({
  selector: 'app-statistic-for-match',
  templateUrl: './statistic-for-match.component.html',
  styleUrls: ['./statistic-for-match.component.css']
})
export class StatisticForMatchComponent implements OnInit {

  @Input() match: Match;
  @Input() matchDetails: MatchDetail[];
  constructor(private matchService: MatchService,
              private matchDetailService: MatchDetailsService) { }

  ngOnInit() {
  }

}
