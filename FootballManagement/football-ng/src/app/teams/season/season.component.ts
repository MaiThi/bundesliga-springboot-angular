import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Tournament} from '../../Model/tournament';

@Component({
  selector: 'app-season',
  templateUrl: './season.component.html',
  styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {

  @Input() season: Tournament;
  @Output() updatedSeason: EventEmitter<Tournament> = new EventEmitter<Tournament>();
  constructor() { }

  ngOnInit() {
  }

  updateSeason(s: Tournament) {
    this.updatedSeason.emit(s);
  }
}
