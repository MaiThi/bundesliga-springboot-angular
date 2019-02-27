import {Component, Inject, Input, OnInit} from '@angular/core';
import {NotificationService} from '../../ApiService/notification.service';
import {MatchDetailsService} from '../../ApiService/match-details.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {PlayerInMatch} from '../../Model/player-in-match';
import {MatchService} from '../../ApiService/match.service';
import {Match} from '../../Model/match';
import {PlayerInMatchService} from '../../ApiService/player-in-match.service';

@Component({
  selector: 'app-add-match-detail',
  templateUrl: './add-match-detail.component.html',
  styleUrls: ['./add-match-detail.component.css']
})
export class AddMatchDetailComponent implements OnInit {
  playerFilters: PlayerInMatch[] = [];
  selectedPlayerInMatch: number;
  typeCards: string[] = ['YELLOW', 'RED'];
  selectedTeam: number;
  matchId: number;
  match: Match;
  isGoal: boolean;
  isFrom11: boolean;
  isBadGoal: boolean;
  isMoveIn: boolean;
  isMoveOut: boolean;

  constructor(private notificationService: NotificationService,
              private matchService: MatchService,
              private playerInMatchService: PlayerInMatchService,
              public matchDetailService: MatchDetailsService,
              @Inject(MAT_DIALOG_DATA) data,
              public dialogRef: MatDialogRef<AddMatchDetailComponent>) {
    this.match = data.match;
  }

  ngOnInit() {
    this.matchId = this.match.id;
    this.isGoal = this.matchDetailService.form.controls['is_Goal'].value;
    this.isFrom11 = this.matchDetailService.form.controls['is_goal_from_11m'].value;
    this.isBadGoal = this.matchDetailService.form.controls['is_the_bad_goal'].value;
    this.isMoveIn = this.matchDetailService.form.controls['is_MoveIn'].value;
    this.isMoveOut = this.matchDetailService.form.controls['is_MoveOut'].value;
  }

  onClear() {
    this.matchDetailService.form.reset();
  }

  onClose() {
    this.matchDetailService.form.reset();
    this.dialogRef.close();
  }

  onSubmit() {
    this.matchDetailService.form.controls['is_Goal'].setValue(this.isGoal);
    this.matchDetailService.form.controls['is_goal_from_11m'].setValue(this.isFrom11);
    this.matchDetailService.form.controls['is_the_bad_goal'].setValue(this.isBadGoal);
    this.matchDetailService.form.controls['playInMatchId'].setValue(this.selectedPlayerInMatch);
    this.matchDetailService.form.controls['teamId'].setValue(this.selectedTeam);
    this.matchDetailService.form.controls['is_MoveIn'].setValue(this.isMoveIn);
    this.matchDetailService.form.controls['is_MoveOut'].setValue(this.isMoveOut)
    this.matchService.form.updateValueAndValidity();
    if (this.matchDetailService.form.controls['id'].value == null) {
      this.matchDetailService.addMatchDetail(this.matchDetailService.form.value).subscribe(
        res => {
          this.matchDetailService.form.setValue(res);
          this.notificationService.success(':: Adding successfully');
          this.dialogRef.close(this.matchDetailService.form.value);
        },
        error1 => {
          this.notificationService.warn(':: Adding new detail unsuccessfully');
        }
      );
    } else {
      this.matchDetailService.updateMatchDetail(this.matchDetailService.form.value).subscribe(
        res => {
            this.notificationService.success(':: Update info successfully');
            this.dialogRef.close(this.matchDetailService.form.value);
        },
        error1 => {
          this.notificationService.warn(':: Adding match detail unsuccessfully');
        }
      );
    }
  }

  filterPlayer() {
    this.playerInMatchService.getPlayerInMatchByMatchIdAndTeamTourId(this.matchId, this.selectedTeam).subscribe(
      res => {
          this.playerFilters = res;
      },
      error1 => {
        this.notificationService.warn(':: Getting players in match unsuccessfully');
      }
    );
  }
}
