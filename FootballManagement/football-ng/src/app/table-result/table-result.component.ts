import { Component, OnInit } from '@angular/core';
import {TeamInTournament} from '../Model/team-in-tournament';
import {ApiServiceService} from '../ApiService/api-service.service';

@Component({
  selector: 'app-table-result',
  templateUrl: './table-result.component.html',
  styleUrls: ['./table-result.component.css']
})
export class TableResultComponent implements OnInit {

  teamsSeason: TeamInTournament[] = [];
  currentTournament: number;
  constructor(private apiService: ApiServiceService) { }

  ngOnInit() {
    this.currentTournament = 48;
    this.getTeamBySeason(this.currentTournament);
  }
  getTeamBySeason(ss: number) {
    this.apiService.getTeamsByTournamentId(ss).subscribe(
      res => {
        this.teamsSeason = res;
      },
      error1 => {
        alert('An error has occurred');
      }
    );
  }
}
