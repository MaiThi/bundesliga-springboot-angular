import {Component, OnInit, ViewChild} from '@angular/core';
import {Weekno} from '../ApiService/weekno.enum';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Match} from '../Model/match';
import {NotificationService} from '../ApiService/notification.service';
import {MatchService} from '../ApiService/match.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';


@Component({
  selector: 'app-matches-admin',
  templateUrl: './matches-admin.component.html',
  styleUrls: ['./matches-admin.component.css']
})
export class MatchesAdminComponent implements OnInit {

  isLoggedIn = false;

  displayedColumns: string[] = ['happenedDate', 'homeTeamName', 'visitTeamName',
                                'homeGoals', 'visitGoals', 'locationName', 'processBar', 'actions'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;
  matches: Match[] = [];

  selectedDayNo: string;
  weekDayEnum = Weekno;
  listMatches: MatTableDataSource<any>;
  keys(): Array<string> {
    const keys = Object.keys(this.weekDayEnum);
    return keys.slice(keys.length / 2);
  }
  constructor(private notificationService: NotificationService,
              private matchService: MatchService,
              private tokenStorage: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.selectedDayNo = 'DAY_1';
      this.matchService.getTeamByDayNoAndTournament(48, this.selectedDayNo).subscribe(
        res => {
          this.matches = res;
          this.filterValueIntoMatSource(this.matches);
        },
        error1 => {
          this.notificationService.warn(':: Getting matches unsuccessfully');
        }
      );
    }
  }

  onSearchClear() {
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listMatches.filter = this.searchKey.trim().toLowerCase();
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

  changeValueDayNo(event) {
    this.matches = [];
    this.selectedDayNo = event.target.value;
    this.matchService.getTeamByDayNoAndTournament(48, this.selectedDayNo).subscribe(
      res => {
        this.matches = res;
        this.filterValueIntoMatSource(this.matches);
      },
      error1 => {
        this.notificationService.warn(':: Getting matches unsuccessfully');
      }
    );
  }

  redirectMatchPlayerDetail(row) {
    this.matchService.populateForm(row);
    this.router.navigate(['/combineMatchDetail',  this.matchService.form.controls['id'].value]);
  }
}
