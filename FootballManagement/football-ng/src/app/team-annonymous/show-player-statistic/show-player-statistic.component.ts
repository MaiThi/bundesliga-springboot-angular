import {Component, Input, OnInit} from '@angular/core';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {PlayerSeasonService} from '../../ApiService/player-season.service';
import {PlayerInTeamTournament} from '../../Model/player-in-team-tournament';

@Component({
  selector: 'app-show-player-statistic',
  templateUrl: './show-player-statistic.component.html',
  styleUrls: ['./show-player-statistic.component.css']
})
export class ShowPlayerStatisticComponent implements OnInit {

  @Input() teamTour: TeamInTournament;
  playerSS: PlayerInTeamTournament[] = [];
  constructor(private playerSeasonService: PlayerSeasonService) { }

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
  ];

  ngOnInit() {
    this.playerSeasonService.getPlayersByTeamTourId(this.teamTour.id).subscribe(
      res => {
        this.playerSS = res;
      },
      error1 => {}
    );
  }

}
