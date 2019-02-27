import {Component, Inject, OnInit} from '@angular/core';
import {NotificationService} from '../../ApiService/notification.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {PlayerSeasonService} from '../../ApiService/player-season.service';
import {Player} from '../../Model/player';
import {PlayerService} from '../../ApiService/player.service';

@Component({
  selector: 'app-add-player-into-season',
  templateUrl: './add-player-into-season.component.html',
  styleUrls: ['./add-player-into-season.component.css']
})
export class AddPlayerIntoSeasonComponent implements OnInit {

  players: Player[] = [];
  teamTourId: number;
  constructor(private playerService: PlayerService,
              public playerSeasonService: PlayerSeasonService,
              private notificationService: NotificationService,
              @Inject(MAT_DIALOG_DATA) data,
              public dialogRef: MatDialogRef<AddPlayerIntoSeasonComponent>) {
      this.teamTourId = data.teamTourId;
  }

  ngOnInit() {
    this.playerSeasonService.getPlayersForAdding(this.teamTourId).subscribe(
      res => {
          this.players = res;
          alert(this.players.length);
      },
      error1 => {}
    );
  }

  onClear() {
    this.playerSeasonService.formPlayerSeason.value.playId = null;
    this.playerSeasonService.formPlayerSeason.value.nationality = '';
    this.playerSeasonService.formPlayerSeason.value.birthday = null;
    this.playerSeasonService.formPlayerSeason.value.nbClothes = 0;
    this.notificationService.success(':: Clear successfully');
  }

  onClose() {
    this.playerSeasonService.formPlayerSeason.reset();
    this.playerSeasonService.initializeFormGroup();
    this.dialogRef.close(null);
  }

  onSubmit() {
    if (this.playerSeasonService.formPlayerSeason.value.id == null) {
      this.playerSeasonService.postPlayer(this.playerSeasonService.formPlayerSeason.value).subscribe(
        res => {
          this.notificationService.success(':: Submit successfully');
          this.dialogRef.close(this.playerSeasonService.formPlayerSeason.value);
        },
        error1 => {
          this.notificationService.warn(':: Adding unsuccessfully');
        }
      );
    } else {
      this.playerSeasonService.putPlayer(this.playerSeasonService.formPlayerSeason.value).subscribe(
        res => {
          this.notificationService.success(':: Submit successfully');
          this.dialogRef.close(this.playerSeasonService.formPlayerSeason.value);
        },
        error1 => {
          this.notificationService.warn(':: Editing unsuccessfully');
        }
      );
    }
  }

  getPlayerInfor(event: any) {
    this.playerService.getPlayerById(event).subscribe(
      res => {
        const player: Player = res;
        this.playerSeasonService.formPlayerSeason.controls['nationality'].setValue(player.nationality);
        this.playerSeasonService.formPlayerSeason.updateValueAndValidity();
      },
      error1 => {}
    );
  }
}
