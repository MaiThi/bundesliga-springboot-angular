import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Match} from '../../Model/match';
import {MatchService} from '../../ApiService/match.service';
import {NotificationService} from '../../ApiService/notification.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {PlayerInMatch} from '../../Model/player-in-match';
import {PlayerInMatchService} from '../../ApiService/player-in-match.service';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-players-in-match',
  templateUrl: './players-in-match.component.html',
  styleUrls: ['./players-in-match.component.css']
})
export class PlayersInMatchComponent implements OnInit {

  @Input() matchId: number;
  @Input() teamSeasonId: number;
  @Input() match: Match;
  playerInMatch: PlayerInMatch[] = [];
  searchKey: string;
  matchesJoinedBefore: Match[] = [];
  selectedMatch: number;

  listPlayerMatch: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['select', 'nbClothes', 'playerName', 'positionName',
    'goals', 'yellowCards', 'redCards', 'actions'];
  selection = new SelectionModel<PlayerInMatch>(true, []);

  constructor(private matchService: MatchService,
              private playerInMatchService: PlayerInMatchService,
              private notificationService: NotificationService) { }

  ngOnInit() {
    this.playerInMatchService.getPlayerInMatchByMatchIdAndTeamTourId(this.matchId, this.teamSeasonId).subscribe(
      res => {
        this.playerInMatch = res;
        this.filterValueIntoPlayerMatches(this.playerInMatch);
      },
      error1 => {
      }
    );
    this.matchService.getMatchesAlreadyAssignPlayers(this.teamSeasonId).subscribe(
      res => {
          this.matchesJoinedBefore = res;
          if (this.matchesJoinedBefore != null && this.matchesJoinedBefore.length !== 0) {
            this.selectedMatch = this.matchesJoinedBefore[0].id;
          }
      },
      error1 => {
        alert('wrong here');
      }
    );
  }

  filterValueIntoPlayerMatches (playerInMatches: PlayerInMatch[]) {
    this.listPlayerMatch = new MatTableDataSource(playerInMatches);
    this.listPlayerMatch.sort = this.sort;
    this.listPlayerMatch.paginator = this.paginator;
    this.listPlayerMatch.filterPredicate =
      (data: PlayerInMatch, filter: string) => (data.playerName !== null) ? data.playerName.toLowerCase().indexOf(filter) !== -1 : false;
    if (this.listPlayerMatch.filteredData.length === 0 ) {
      this.listPlayerMatch.filterPredicate =
        (data: PlayerInMatch, filter: string) => (data.positionName !== null) ?
          data.positionName.toLowerCase().indexOf(filter) !== -1 : false;
    }
  }

  onSearchClear() {
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listPlayerMatch.filter = this.searchKey.trim().toLowerCase();
  }

  addPlayerAuto() {
    this.playerInMatchService.postCreateByMatchAndTeamTournament(this.matchId, this.teamSeasonId).subscribe(
      res => {
        this.playerInMatch = res;
        this.filterValueIntoPlayerMatches(this.playerInMatch);
      },
      error1 => {

      }
    );
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.listPlayerMatch.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.listPlayerMatch.data.forEach(row => this.selection.select(row));
  }

  removeSelectedPlayer() {
    if (confirm('Are you sure that you want to remove these players in the match?')) {
      this.selection.selected.forEach(item => {
        this.playerInMatchService.deletePlayerInMatchById(item.id).subscribe(
          res => {
            const index: number = this.playerInMatch.findIndex(pl => pl === item);
            console.log(this.playerInMatch.findIndex(d => d === item));
            this.playerInMatch.splice(index, 1);

            this.filterValueIntoPlayerMatches(this.playerInMatch);
          },
          error1 => {
          }
        );

      });
      this.selection = new SelectionModel<PlayerInMatch>(true, []);
    }
  }


  onDelete(row: PlayerInMatch) {
    if (confirm('Are you sure that you want to delete this player in the match?')) {
        this.playerInMatchService.deletePlayerInMatchById(row.id).subscribe(
          res => {
            const index: number = this.playerInMatch.indexOf(row);
            this.playerInMatch.splice(index, 1);
            this.filterValueIntoPlayerMatches(this.playerInMatch);
          },
          error1 => {
            this.notificationService.warn(':: Delete info unsuccessfully');
          }
        );
    }
  }

  onEdit(row: PlayerInMatch) {
    this.playerInMatchService.putPlayerInMatch(row).subscribe(
      res => {
        this.notificationService.success(':: Update info successfully');
      },
      error1 => {
        this.notificationService.warn(':: Update info unsuccessfully');
      }
    );
  }

  addPlayersBasedOnPreviousMatch() {
    this.playerInMatchService.postCreateByThePreviousMatch(this.selectedMatch, this.matchId).subscribe(
      res => {
          this.playerInMatch = res;
          this.filterValueIntoPlayerMatches(this.playerInMatch);
          this.notificationService.success(':: Update info successfully');
      },
      error1 => {
        this.notificationService.warn(':: Update info unsuccessfully');
      }
    );
  }
}
