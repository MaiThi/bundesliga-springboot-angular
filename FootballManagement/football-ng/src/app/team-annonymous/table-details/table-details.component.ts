import {Component, Input, OnInit} from '@angular/core';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {ApiServiceService} from '../../ApiService/api-service.service';

@Component({
  selector: 'app-table-details',
  templateUrl: './table-details.component.html',
  styleUrls: ['./table-details.component.css']
})
export class TableDetailsComponent implements OnInit {

  @Input() id: number;

  tournamentId: number;
  teamTourId: number;
  currentTeamTour: TeamInTournament;
  teamsSeason: TeamInTournament[] = [];
  constructor(private apiService: ApiServiceService) { }

  ngOnInit() {
    this.tournamentId = 48;
    // const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.teamTourId = this.id;
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
