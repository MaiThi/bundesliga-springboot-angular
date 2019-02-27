import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Team} from '../../Model/team';
import {ApiServiceService} from '../../ApiService/api-service.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {AddLocationComponent} from '../add-location/add-location.component';

@Component({
  selector: 'app-teams-info',
  templateUrl: './teams-info.component.html',
  styleUrls: ['./teams-info.component.css']
})
export class TeamsInfoComponent implements OnInit {
  teams: Team[] = [];
  constructor(private apiService: ApiServiceService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.getAllTeams();
  }

  getAllTeams() {
    this.apiService.getAllTeams().subscribe(
      res => {
        this.teams = res;
      },
      error1 => {
        alert('An error has occurred');
      }
    );
  }

  updateTeam(updatedTeam: Team) {
    this.apiService.postTeam(updatedTeam).subscribe(
      res => {},
      error1 => {
        alert('Could not update Team infor');
      }
    );
  }

  deleteTeam(deletedTeam: Team) {
    if (confirm('Are you sure that you want to delete this team?')) {
      this.apiService.deleteTeam(deletedTeam.id).subscribe(
        res => {
          const indexOfTeam = this.teams.indexOf(deletedTeam);
          this.teams.splice(indexOfTeam, 1);
        },
        error1 => {
          alert('Could not delete this Team');
        }
      );
    }
  }


  createNewTeam() {
    const newTeam: Team = {
      id: null,
      name: 'New Team',
      logoTeam: 'temp-icon.png',
      color: 'red'
    };
    this.apiService.postTeam(newTeam).subscribe(
      res => {
        const teamNew: Team = res;
        this.teams.splice(0, 0, teamNew);
        // Open dialog for adding new Location.
        // Because 1 team have 1 their own stadium
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.width = '70%';
        dialogConfig.data = {
          teamId: teamNew.id
        };
        this.dialog.open(AddLocationComponent, dialogConfig);
      },
      error1 => {
        alert('Could not save new team');
      }
    );
  }
}
