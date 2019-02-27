import {Component, Input, OnInit} from '@angular/core';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {Router} from '@angular/router';

@Component({
  selector: 'app-card-team',
  templateUrl: './card-team.component.html',
  styleUrls: ['./card-team.component.css']
})
export class CardTeamComponent implements OnInit {

  constructor(private router: Router) { }
  @Input() item: TeamInTournament;
  ngOnInit() {
  }
  redirectShowDetails() {
    this.router.navigate(['/showDetailTeamTour',  this.item.id]);
  }
}
