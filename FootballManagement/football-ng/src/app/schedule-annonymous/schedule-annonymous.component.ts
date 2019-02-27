import {Component, ElementRef, OnInit} from '@angular/core';
import {Tournament} from '../Model/tournament';
import {TeamInTournament} from '../Model/team-in-tournament';
import {Weekno} from '../ApiService/weekno.enum';
import {ApiServiceService} from '../ApiService/api-service.service';
import {MatchService} from '../ApiService/match.service';
import {Match} from '../Model/match';
import * as moment from 'moment';

@Component({
  selector: 'app-schedule-annonymous',
  templateUrl: './schedule-annonymous.component.html',
  styleUrls: ['./schedule-annonymous.component.css']
})
export class ScheduleAnnonymousComponent implements OnInit {

  selectedTournament: number;
  currentTournament: Tournament;
  listTeamsInTour: TeamInTournament[] = [];
  isSelectTeam: number;

  listMatch: Match[] = [];
  setListDay: Date[] = [];

  formatsDateTest: string[] = [
    'dd/MM/yyyy',
    'EEE,  dd/MM/yyyy | hh:mm',
    'dd-MM-yyyy',
    'EEE,  dd-MM-yyyy | HH:mm',
    'MM/dd/yyyy',
    'EEE, MM/dd/yyyy | hh:mm',
    'yyyy/MM/dd',
    'EEE, yyyy/MM/dd | HH:mm',
    'dd/MM/yy',
    'EEE, dd/MM/yy | hh:mm',
  ];
  days;
  selectedDayNo: string;
  selectedTeam: number;

  weekDayEnum = Weekno;
  keys(): Array<string> {
    const keys = Object.keys(this.weekDayEnum);
    return keys.slice(keys.length / 2);
  }

  constructor(private apiService: ApiServiceService,
              private matchService: MatchService,
              private elementRef: ElementRef) { }

  ngOnInit() {
    this.isSelectTeam = 0;
    this.selectedTournament = 48;
    this.selectedDayNo = 'DAY_1';
    this.selectedTeam = 0;
    this.apiService.getTournametnById(this.selectedTournament).subscribe(
      res => {
        this.currentTournament = res;
        this.getMatchesByDayNoAndTournamend(this.currentTournament.id, this.selectedDayNo);

        this.apiService.getTeamsByTournamentId(this.selectedTournament).subscribe(
          res1 => {
            this.listTeamsInTour = res1;
          },
          error2 => {}
        );
      },
      error1 => {
          alert('There is an errror when getting tournament info');
      }
    );
  }
  ngAfterViewIni() {
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#f2f2f2';
  }

  getMatchesByDayNoAndTournamend(tournamentId: number, dayNo: string) {
      this.listMatch = [];
      this.matchService.getTeamByDayNoAndTournament(tournamentId, dayNo).subscribe(
        res => {
            this.listMatch = res;
            for (let i = 0; i < this.listMatch.length; i++) {
                this.setListDay.push(this.listMatch[i].happenedDate);
            }
          this.days = new Set(this.setListDay);

        },
        error1 => {
          alert('There is some error occurred');
        }
      );
  }


  selectedTeamChanged(event ) {
    this.isSelectTeam = 1;
    this.listMatch = [];
    this.selectedTeam = event.target.value;
    this.matchService.getMatchesByTeamTourForHomeAndVisitTeam(this.selectedTeam).subscribe(
      res => {
        this.listMatch = res;
      },
      error1 => {}
    );
  }

  changeValueDayNo(event) {
    this.isSelectTeam = 0;
    this.setListDay = [];
    this.selectedDayNo = event.target.value;
    this.getMatchesByDayNoAndTournamend(this.currentTournament.id, this.selectedDayNo);
  }
}
