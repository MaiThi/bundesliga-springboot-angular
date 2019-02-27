import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Team} from '../../Model/team';
import {TeamInTournament} from '../../Model/team-in-tournament';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  @Input() tess: TeamInTournament[];
  @Input() title: string;
  @Output() findedTeam: EventEmitter<TeamInTournament> = new EventEmitter<TeamInTournament>();
  @Output() deletedTeamSeason: EventEmitter<TeamInTournament> = new EventEmitter<TeamInTournament>();
  @Output() updatedTeamSeason: EventEmitter<TeamInTournament> = new EventEmitter<TeamInTournament>();
  constructor() { }

  ngOnInit() {
  }


  findingTeam(t: TeamInTournament) {
      this.findedTeam.emit(t);
  }

  deletingTeam (t: TeamInTournament) {
    if (confirm('Are you sure that you want to delete this Team in the Season?')) {
      this.deletedTeamSeason.emit(t);
    }
  }

  updateTeamInTournament(t: TeamInTournament) {
    this.updatedTeamSeason.emit(t);
  }
}
