import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiServiceService} from '../ApiService/api-service.service';
import {Tournament} from '../Model/tournament';
import {NotificationService} from '../ApiService/notification.service';
import {MatchService} from '../ApiService/match.service';
import {Weekno} from '../ApiService/weekno.enum';
import {Match} from '../Model/match';
import {MatDialog, MatDialogConfig, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {_isNumberValue} from '@angular/cdk/coercion';
import {PlayerComponent} from '../player-admin/player/player.component';
import {Player} from '../Model/player';
import {EditMatchComponent} from './edit-match/edit-match.component';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';
import * as moment from 'moment';

@Component({
  selector: 'app-schedule-admin',
  templateUrl: './schedule-admin.component.html',
  styleUrls: ['./schedule-admin.component.css']
})
export class ScheduleAdminComponent implements OnInit {

  isLoggedIn = false;
  displayedColumns: string[] = ['happenedDate', 'homeTeamName', 'visitTeamName', 'homeGoals', 'visitGoals', 'locationName', 'actions'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;
  matches: Match[] = [];
  dateMatches: string[] = [];
  teamAldreadyArrangeTimeINDayNo: string [] = [];

  tournaments: Tournament[] = [];
  selectedSeason: number;
  bolAlreadyCreatedMatches: number;

  disableDayNo: number;
  selectedDayNo: string;
  weekDayEnum = Weekno;
  listMatches: MatTableDataSource<any>;
  keys(): Array<string> {
    const keys = Object.keys(this.weekDayEnum);
    return keys.slice(keys.length / 2);
  }

  constructor(private apiService: ApiServiceService,
              private notificationService: NotificationService,
              private matchService: MatchService,
              private dialog: MatDialog,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.getTopTournament();
    this.bolAlreadyCreatedMatches = 1;
    this.selectedDayNo = 'DAY_1';
    this.disableDayNo = 1;
    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
    }
  }
  getTopTournament() {
    this.apiService.getTopTournament().subscribe(
      res => {
        this.tournaments = res;
        this.selectedSeason = this.tournaments[0].id;
      },
      error1 => {
         this.notificationService.warn(':: Finding Tournaments unsuccessfully');
      }
    );
  }

  createAllMatches() {
      this.matchService.initializeMatchesByTournamentId(this.selectedSeason).subscribe(
        res => {
          this.matches = res;
          this.filterValueIntoMatSource(this.matches);
        },
        error1 => {}
      );
  }

  filterValueIntoMatSource(matches: Match[]) {
    this.listMatches = new MatTableDataSource(matches);
    this.listMatches.sort = this.sort;
    this.listMatches.paginator = this.paginator;
    this.listMatches.filterPredicate =
      (data: Match, filter: string) => (data.homeTeamName !== null) ? data.homeTeamName.toLowerCase().indexOf(filter) !== -1 : false;
    if (this.listMatches.filteredData.length === 0 ) {
      this.listMatches.filterPredicate =
        (data: Match, filter: string) => (data.visitTeamName !== null) ? data.visitTeamName.toLowerCase().indexOf(filter) !== -1 : false;
    }
  }

  selectedSeasonChange(event: any) {
    if (event.target.value !== 'Please choose tournament to create matches') {
      this.selectedSeason = event.target.value;
      this.disableDayNo = 0;
      this.matchService.getCheckAlreadyCreatedMatches(this.selectedSeason).subscribe(
        res => {
          this.bolAlreadyCreatedMatches = res;
          if (this.bolAlreadyCreatedMatches === 0) {
            this.matches = [];
            this.filterValueIntoMatSource(this.matches);
          } else {
            this.matchService.getMatchesByTournamentId(this.selectedSeason).subscribe(
              resAllMatches => {
                this.matches = resAllMatches;
                this.filterValueIntoMatSource(this.matches);
              }
            );
          }
        },
        error1 => {
          this.notificationService.warn(':: Checked Already Existing Matches unsuccessfully');
        }
      );
    } else {
      this.disableDayNo = 1;
    }
  }

  onSearchClear() {
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listMatches.filter = this.searchKey.trim().toLowerCase();
  }

  addMatchIntoWeekPlay() {
    this.matchService.initializeFormGroup(this.selectedSeason, this.selectedDayNo);
    this.matchService.form.controls['tournamentId'].setValue(this.selectedSeason);
    this.matchService.form.controls['dayNo'].setValue(this.selectedDayNo);
    this.matchService.form.updateValueAndValidity();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '70%';
    dialogConfig.data = {
      samDate: new Set(this.dateMatches),
      teamArranged: this.teamAldreadyArrangeTimeINDayNo
    }
    const dialogRef = this.dialog.open(EditMatchComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => this.refreshData(data)
    );
  }
  refreshData(data: Match) {
    this.dateMatches.push(moment(data.happenedDate).format('YYYY-MM-DD HH:mm'));
    this.teamAldreadyArrangeTimeINDayNo.push(data.homeTeamName);
    this.teamAldreadyArrangeTimeINDayNo.push(data.visitTeamName);
    this.matchService.getTeamByDayNoAndTournament(data.tournamentId, data.dayNo).subscribe(
      res => {
          this.matches = res;
          this.filterValueIntoMatSource(this.matches);
      },
      error1 => {}
    );
  }


  changeValueDayNo(event: any) {
      this.matches = [];
      this.teamAldreadyArrangeTimeINDayNo = [];
      this.selectedDayNo = event.target.value;
      this.dateMatches = [];
      this.matchService.getTeamByDayNoAndTournament(this.selectedSeason, this.selectedDayNo).subscribe(
        res => {
          this.matches = res;
          this.disableDayNo = 0;
          this.filterValueIntoMatSource(this.matches);
          if (this.matches.length !== 0) {
            for (let i = 0; i < this.matches.length; i++) {
              if (this.matches[i].happenedDate !== null) {
                this.teamAldreadyArrangeTimeINDayNo.push(this.matches[i].homeTeamName);
                this.teamAldreadyArrangeTimeINDayNo.push(this.matches[i].visitTeamName);
                this.dateMatches.push(moment(this.matches[i].happenedDate).format('YYYY-MM-DD HH:mm'));
              }
            }
          }
        },
        error1 => {}
      );
  }

  onEdit(row) {
    this.matchService.populateForm(row);
    this.matchService.form.controls['tournamentId'].setValue(this.selectedSeason);
    this.matchService.form.controls['dayNo'].setValue(this.selectedDayNo);
    this.matchService.form.controls['happenedDate'].setValue(this.matchService.form.value.happenedDate);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '70%';
    dialogConfig.data = {
      samDate: new Set(this.dateMatches),
      teamArranged: this.teamAldreadyArrangeTimeINDayNo
    }
    this.dialog.open(EditMatchComponent, dialogConfig).afterClosed().subscribe(
      data => this.refreshData(data)
    );
  }

}
