import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {ApiServiceService} from '../../ApiService/api-service.service';
import {Team} from '../../Model/team';

@Component({
  selector: 'app-show-details-for-team',
  templateUrl: './show-details-for-team.component.html',
  styleUrls: ['./show-details-for-team.component.css']
})
export class ShowDetailsForTeamComponent implements OnInit {


  tournamentId: number;
  teamTourId: number;
  currentTeamTour: TeamInTournament;
  teamsSeason: TeamInTournament[] = [];
  constructor(private route: ActivatedRoute,
              private apiService: ApiServiceService) { }

  ngOnInit() {
    this.tournamentId = 48;
    const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.teamTourId = id;
    this.apiService.getTeamTourById(this.teamTourId).subscribe(
      res => {
        this.currentTeamTour = res;
      },
      error1 => {}
    );
    this.getTeamBySeason(this.tournamentId, this.teamTourId);
  }
  getTeamBySeason(ss: number, teamTourId) {
    this.apiService.getTeamTourByTourIdAndTeamId(ss, teamTourId).subscribe(
      res => {
        this.teamsSeason = res;
      },
      error1 => {
        alert('An error has occurred');
      }
    );
  }
}
