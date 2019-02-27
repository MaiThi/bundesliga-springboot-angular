import {Component, Inject, OnInit} from '@angular/core';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {Match} from '../../Model/match';
import {ApiServiceService} from '../../ApiService/api-service.service';
import {MatchService} from '../../ApiService/match.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotificationService} from '../../ApiService/notification.service';
import * as moment from 'moment';

@Component({
  selector: 'app-edit-match',
  templateUrl: './edit-match.component.html',
  styleUrls: ['./edit-match.component.css']
})
export class EditMatchComponent implements OnInit {

  teams: TeamInTournament[] = [];
  teamsFilters: Match[] = [];
  visitTeam: number;

  sameDayTeam: string[];
  selectDate: string;

  teamArrangedTime: string[];
  dateTime: Date;
  constructor(private apiService: ApiServiceService,
              public matchService: MatchService,
              private notificationService: NotificationService,
              @Inject(MAT_DIALOG_DATA) data,
              public dialogRef: MatDialogRef<EditMatchComponent>) {
    this.sameDayTeam = data.samDate;
    this.teamArrangedTime = data.teamArranged;
  }

  ngOnInit() {
    this.teams = [];
    if (this.matchService.form.controls['visitTeamId'].value !== 999) {
      this.getListForUpdate();
      this.visitTeam = this.matchService.form.controls['visitTeamId'].value;
      this.dateTime = new Date(moment(this.matchService.form.controls['happenedDate'].value).format('YYYY-MM-DD HH:mm'));
      this.apiService.getTeamsByTournamentId(this.matchService.form.controls['tournamentId'].value).subscribe(
        res => {
          this.teams = res;
        },
        error1 => {}
      );
    } else {
      this.dateTime =  new Date(moment(this.matchService.form.controls['happenedDate'].value).format('YYYY-MM-DD HH:mm'));
      this.apiService.getTeamsByTournamentId(this.matchService.form.controls['tournamentId'].value).subscribe(
        res => {
          const teamInTournaments: TeamInTournament[] = res;

          if (this.teamArrangedTime != null && this.teamArrangedTime.length !== 0) {
            for (let i = 0; i < teamInTournaments.length; i++) {
              if (! this.teamArrangedTime.includes(teamInTournaments[i].teamName))  {
                this.teams.push(teamInTournaments[i]);
              }
            }
          } else {
            this.teams = teamInTournaments;
          }
        },
        error1 => {
          this.notificationService.warn(':: Getting List Teams unsuccessfully');
        }
      );
    }
  }

  onSubmit() {
    if (this.selectDate !== undefined || this.selectDate != null) {
      this.matchService.form.controls['happenedDate'].setValue(new Date(this.selectDate));
    } else {
      this.dateTime.setSeconds(0);
      this.matchService.form.controls['happenedDate'].setValue(this.dateTime);
    }
    this.matchService.form.controls['visitTeamId'].setValue(this.visitTeam);

    this.matchService.form.updateValueAndValidity();
    this.matchService.getMatchesByHomeTeamAndVisitteam(this.matchService.form.controls['homeTeamId'].value,
                        this.matchService.form.controls['visitTeamId'].value).subscribe(
      res1 => {
        const ma: Match = res1;
        this.matchService.form.controls['id'].setValue(ma.id);
        this.matchService.form.updateValueAndValidity();
        this.matchService.updateMatches(this.matchService.form.value).subscribe(
          res => {
            const mat: Match = res;
            this.matchService.form.value.id = mat.id;
            this.matchService.form.value.locationName = mat.locationName;
            this.matchService.form.value.homeTeamName = mat.homeTeamName;
            this.matchService.form.value.visitTeamName = mat.visitTeamName;
            this.notificationService.success(':: Adding successfully');
            this.dialogRef.close(this.matchService.form.value);
          },
          error1 => {
            this.notificationService.warn(':: Adding unsuccessfully');
          }
        );
      },
      error2 => {}
    );
  }

  onClear() {
    this.matchService.form.reset();
  }

  getListForUpdate() {
    this.matchService.getVisitTeamNotYetArrangedTimePlaying(this.matchService.form.controls['homeTeamId'].value, 1).subscribe(
      res => {
        this.teamsFilters = res;
      },
      error1 => {
        this.notificationService.warn(':: Getting visit team unsuccessfully');
      }
    );
  }

  getListVisitTeam() {
    this.teamsFilters = [];
    this.matchService.getVisitTeamNotYetArrangedTimePlaying(this.matchService.form.controls['homeTeamId'].value, 0).subscribe(
      res => {
        const visitMatchs: Match[] = res;
        if (this.teamArrangedTime != null && this.teamArrangedTime.length !== 0) {
          if (visitMatchs != null && visitMatchs.length !== 0) {
              for (let i = 0; i < visitMatchs.length; i++) {
                if (!this.teamArrangedTime.includes(visitMatchs[i].visitTeamName)) {
                    this.teamsFilters.push(visitMatchs[i]);
                }
              }
              this.visitTeam = this.teamsFilters[0].visitTeamId;
           }
        } else {
          this.teamsFilters = visitMatchs;
        }
        },
      error1 => {
        this.notificationService.warn(':: Getting visit team unsuccessfully');
      }
    );
  }

  onClose() {
    this.matchService.form.reset();
    this.dialogRef.close();
  }
}
