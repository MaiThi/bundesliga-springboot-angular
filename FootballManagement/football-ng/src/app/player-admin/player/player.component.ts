import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {PlayerService} from '../../ApiService/player.service';
import {NotificationService} from '../../ApiService/notification.service';
import {Player} from '../../Model/player';
import * as moment from 'moment';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  constructor(public playerService: PlayerService,
              private notificationService: NotificationService,
              public dialogRef: MatDialogRef<PlayerComponent>) { }

  ngOnInit() {
    if (this.playerService.form.controls['dateOfBirth'].value != null) {
     this.playerService.form.controls['dateOfBirth'].setValue
                          (new Date(moment(this.playerService.form.controls['dateOfBirth'].value).format('YYYY-MM-DD')));
    }
  }

  onClear() {
    this.playerService.form.reset();
    this.playerService.initializeFormGroup();
    this.notificationService.success(':: Submitted successfully');
  }

  onSubmit() {
    if (this.playerService.form.valid) {
      if (this.playerService.form.value.id == null) {
        this.playerService.postPlayer(this.playerService.form.value).subscribe(
          res => {
            const player: Player = res;
            this.playerService.form.value.id = player.id;
            this.notificationService.success(':: Adding successfully');
            this.dialogRef.close(this.playerService.form.value);
          },
          error1 => {
            this.notificationService.warn(':: Submitted unsuccessfully');
          }
        );
      } else {
        this.playerService.putPlayer(this.playerService.form.value).subscribe(
          res1 => {
            this.notificationService.success(':: Update successfully');
            this.dialogRef.close();
          },
          error2 => {
            this.notificationService.warn(':: Submitted unsuccessfully');
          }
        );
      }
    }
  }

  onClose() {
    this.playerService.form.reset();
    this.playerService.initializeFormGroup();
    this.dialogRef.close();
  }
}
