import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Location} from '../../Model/location';
import {LocationService} from '../../ApiService/location.service';
import {NotificationService} from '../../ApiService/notification.service';


@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.css']
})
export class AddLocationComponent implements OnInit {
  locationName: string;
  address: string;
  description: string;
  numberPP: number;
  teamId: number;
  constructor(public dialogRef: MatDialogRef<AddLocationComponent>,
              private notificationService: NotificationService,
              private locationService: LocationService,
              @Inject(MAT_DIALOG_DATA) data) {
    this.teamId = data.teamId;
  }

  ngOnInit() {
    this.numberPP = 0;
  }

  onSubmit() {
      const location: Location = {
        id: null,
        name: this.locationName,
        address: this.address,
        description: this.description,
        nbPeopleHolded: this.numberPP,
        teamId: this.teamId,
        image: 'background.jpg',
      };
      this.locationService.postLocation(location).subscribe(
        res => {
          this.notificationService.success(':: Adding successfully');
          this.dialogRef.close();
        },
        error1 => {}
      );
  }

  onClose() {
    this.dialogRef.close();
  }

  onClear() {
    this.address = '';
    this.description = '';
    this.locationName = '';
    this.numberPP = 0;
  }
}
