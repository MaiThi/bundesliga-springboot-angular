import { Component, OnInit } from '@angular/core';
import {TeamInTournament} from '../Model/team-in-tournament';
import {ApiServiceService} from '../ApiService/api-service.service';

@Component({
  selector: 'app-team-annonymous',
  templateUrl: './team-annonymous.component.html',
  styleUrls: ['./team-annonymous.component.css']
})
export class TeamAnnonymousComponent implements OnInit {

  constructor(private apiService: ApiServiceService) { }
  teamsSeason: TeamInTournament[] = [];
  selectedSeason: number;

  newArray = [];

  ngOnInit() {
    this.selectedSeason = 48;
    this.getTeamBySeason(this.selectedSeason);
  }
  getTeamBySeason(ss: number) {
    this.apiService.getTeamsByTournamentId(ss).subscribe(
      res => {
        this.teamsSeason = res;
        for (let i = 0; i < this.teamsSeason.length; i += 3) {
             this.newArray.push({ items: this.teamsSeason.slice(i, i + 3) });
        }
      },
      error1 => {
        alert('An error has occurred');
      }
    );
  }
}
