import { Component, OnInit } from '@angular/core';
import {Tournament} from '../Model/tournament';
import {ApiServiceService} from '../ApiService/api-service.service';
import {Player} from '../Model/player';
import {MatTableDataSource} from '@angular/material';
import {TeamInTournament} from '../Model/team-in-tournament';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';

@Component({
  selector: 'app-player-admin',
  templateUrl: './player-admin.component.html',
  styleUrls: ['./player-admin.component.css']
})
export class PlayerAdminComponent implements OnInit {
  isLoggedIn = false;
  tournaments: Tournament[] = [];
  selectedSeason: number;
  teamsSeason: TeamInTournament[] = [];
  switchListPage: number;
  constructor(private apiService: ApiServiceService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.getTopTournament();
    this.switchListPage = 0;
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  getTopTournament() {
    this.apiService.getTopTournament().subscribe(
      res => {
        this.tournaments = res;
        this.selectedSeason = this.tournaments[0].id;
        this.getTeamBySeason(this.selectedSeason);
      },
      error1 => {
        alert('An error has occurred');
      }
    );
  }
  getTeamBySeason(ss: number) {
    this.apiService.getTeamsByTournamentId(ss).subscribe(
      res => {
        this.teamsSeason = res;
      },
      error1 => {
        alert('An error has occured');
      }
    );
  }
  selectedSeasonChange(event: any) {
    this.selectedSeason = event.target.value;
    this.getTeamBySeason(this.selectedSeason);
  }

  showPlayersInfo() {
    this.switchListPage = 1;
  }
}
