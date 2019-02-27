import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogConfig, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {MatchService} from '../../ApiService/match.service';
import {NotificationService} from '../../ApiService/notification.service';
import {MatchDetailsService} from '../../ApiService/match-details.service';
import {MatchDetail} from '../../Model/match-detail';
import {Match} from '../../Model/match';
import {AddMatchDetailComponent} from '../add-match-detail/add-match-detail.component';


@Component({
  selector: 'app-match-happened-details',
  templateUrl: './match-happened-details.component.html',
  styleUrls: ['./match-happened-details.component.css']
})
export class MatchHappenedDetailsComponent implements OnInit {

  listMatchDetails: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['action_time', 'teamName', 'playerName', 'is_Goal',
    'cardReceived', 'is_goal_from_11m', 'is_the_bad_goal', 'is_MoveOut', 'is_MoveIn', 'actions'];
  searchKey: string;

  matchId: number;
  @Input() match: Match;
  matchDetails: MatchDetail[] = [];

  constructor(private matchService: MatchService,
              private notificationService: NotificationService,
              private matchDetailService: MatchDetailsService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.matchId = this.match.id;
    this.searchKey = '';
    this.getDetailsFromMatch(this.matchId);
  }

  filterValueIntoMatchDetails (matchDetails: MatchDetail[]) {
    this.listMatchDetails = new MatTableDataSource(matchDetails);
    this.listMatchDetails.sort = this.sort;
    this.listMatchDetails.paginator = this.paginator;
    this.listMatchDetails.filterPredicate =
      (data: MatchDetail, filter: string) => (data.playerName !== null) ? data.playerName.toLowerCase().indexOf(filter) !== -1 : false;
    if (this.listMatchDetails.filteredData.length === 0 ) {
      this.listMatchDetails.filterPredicate =
        (data: MatchDetail, filter: string) => (data.teamName !== null) ? data.teamName.toLowerCase().indexOf(filter) !== -1 : false;
    }
  }

  onSearchClear() {
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listMatchDetails.filter = this.searchKey.trim().toLowerCase();
  }

  onCreate() {
    this.matchDetailService.initializeFormGroup();
    this.matchDetailService.form.controls['happenedDate'].setValue(this.match.happenedDate);
    this.matchService.form.updateValueAndValidity();

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '70%';
    dialogConfig.data = {
      match: this.match
    };
    const dialogRef = this.dialog.open(AddMatchDetailComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
       data => this.pushNewData(data)
    );
  }

  pushNewData(data: MatchDetail) {
    if (data != null) {
      this.matchDetails.push(data);
      this.filterValueIntoMatchDetails(this.matchDetails);
    }
  }
  getDetailsFromMatch(matchId: number) {
    this.matchDetailService.getDetailsByMatchId(this.matchId).subscribe(
      res => {
        this.matchDetails = res;
        this.filterValueIntoMatchDetails(this.matchDetails);
      },
      error1 => {
        this.notificationService.warn(':: Getting match details unsuccessfully');
      }
    );
  }


  onEdit(row) {
    // this.matchDetailService.populateMatchDetail(row);
    // const dialogConfig = new MatDialogConfig();
    // dialogConfig.disableClose = true;
    // dialogConfig.autoFocus = true;
    // dialogConfig.width = '60%';
    // const dialogRef = this.dialog.open(AddMatchDetailComponent, dialogConfig);
    // dialogRef.afterClosed().subscribe(
    //    data => this.getDetailsFromMatch(this.matchId)
    // );
  }

  onDelete(row: MatchDetail) {
    if (confirm('Are you sure that you want to delete this action?')) {
      this.matchDetailService.deteleDetailById(row.id).subscribe(
        res => {
          const indexOf = this.matchDetails.indexOf(row);
          this.matchDetails.splice(indexOf, 1);
          this.filterValueIntoMatchDetails(this.matchDetails);
        },
        error1 => {
          this.notificationService.warn(':: Deleting unsuccessfully');
        }
      );
    }
  }


}
