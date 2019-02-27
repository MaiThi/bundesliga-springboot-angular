import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ApiServiceService} from '../../ApiService/api-service.service';
import {PlayerSeasonService} from '../../ApiService/player-season.service';
import {TeamInTournament} from '../../Model/team-in-tournament';
import {MatDialog, MatDialogConfig, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {PlayerInTeamTournament} from '../../Model/player-in-team-tournament';
import {AddPlayerIntoSeasonComponent} from '../add-player-into-season/add-player-into-season.component';
import {NotificationService} from '../../ApiService/notification.service';
import {Team} from '../../Model/team';

@Component({
  selector: 'app-player-team-season',
  templateUrl: './player-team-season.component.html',
  styleUrls: ['./player-team-season.component.css']
})
export class PlayerTeamSeasonComponent implements OnInit {

  @Input() teamsSeason: TeamInTournament[];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  listPlayerInSS: MatTableDataSource<any>;
  displayedColumns: string[] = ['nbClothes', 'playerName', 'nationality', 'actions'];
  players: PlayerInTeamTournament[] = [];
  searchKey: string;
  selectedTeamTour: TeamInTournament;
  constructor(private apiService: ApiServiceService,
              private playerSeasonService: PlayerSeasonService,
              private notificationService: NotificationService,
              private dialog: MatDialog) { }

  ngOnInit() {

  }

  filterPlayerByTeamTour(s: TeamInTournament) {
    this.selectedTeamTour = s;
    this.playerSeasonService.getPlayersByTeamTourId(s.id).subscribe(
      res => {
        this.players = res;
        if (this.players != null && this.players.length !== 0) {
          this.applytheValueForMatTable(this.players);
        } else {
          this.players = [];
          this.applytheValueForMatTable(this.players);
        }
      },
      error1 => {}
    );
  }

  applytheValueForMatTable(players: PlayerInTeamTournament[]) {
    this.listPlayerInSS = new MatTableDataSource(players);
    this.listPlayerInSS.sort = this.sort;
    this.listPlayerInSS.paginator = this.paginator;
    this.listPlayerInSS.filterPredicate =
      (data: PlayerInTeamTournament, filter: string) =>
        (data.playerName !== null) ? data.playerName.toLowerCase().indexOf(filter) !== -1 : false;
  }

  onSearchClear() {
    // alert(this.searchKey);
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listPlayerInSS.filter = this.searchKey.trim().toLowerCase();
  }

  onDelete(row) {
    if (confirm('Are you sure you want to delete this Player in the team?')) {
      this.playerSeasonService.populateForm(row);
      this.playerSeasonService.deletePlayerById(this.playerSeasonService.formPlayerSeason.value.id).subscribe(
        res => {
          const indexOf = this.players.indexOf(res);
          this.players.splice(indexOf, 1);
          this.applytheValueForMatTable(this.players);
        },
        error1 => {
        }
      );
    }
  }

  onEdit(row) {
    this.playerSeasonService.populateForm(row);
    this.playerSeasonService.formPlayerSeason.controls['teamTourId'].setValue(this.selectedTeamTour.id);
    this.playerSeasonService.formPlayerSeason.controls['playerId'].setValue(this.playerSeasonService.formPlayerSeason.value.playerId);
    this.playerSeasonService.formPlayerSeason.updateValueAndValidity();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    dialogConfig.data = {
      teamTourId: this.selectedTeamTour.id
    }
    const dialogRef = this.dialog.open(AddPlayerIntoSeasonComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => this.refreshData(data)
    );
  }
  reloadData(data: PlayerInTeamTournament) {
    this.filterPlayerByTeamTour(this.selectedTeamTour);
  }

  onCreateSingle() {
      this.playerSeasonService.initializeFormGroup();
      this.playerSeasonService.formPlayerSeason.controls['teamTourId'].setValue(this.selectedTeamTour.id);
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = '60%';
      dialogConfig.data = {
         teamTourId: this.selectedTeamTour.id
      }
      const dialogRef = this.dialog.open(AddPlayerIntoSeasonComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        data => this.refreshData(data)
      );
  }

  refreshData(data: PlayerInTeamTournament) {
    if (data != null) {
      this.filterPlayerByTeamTour(this.selectedTeamTour);
    }
  }

  onCreateMultiple() {
    if (confirm('Are you sure that you want to adding auto all players from the previous season?')) {
      this.playerSeasonService.getTopSeasonOfTeam(this.selectedTeamTour.teamId).subscribe(
        res => {
          const teamTour: TeamInTournament[] = res;
          if (teamTour != null && teamTour.length !== 0) {
            this.playerSeasonService.addPlayersInSeason(teamTour[1].id, this.selectedTeamTour.id).subscribe(
              res1 => {
                this.players = res1;
                this.applytheValueForMatTable(this.players);
                this.notificationService.success(':: Submitted successfully');
              },
              error2 => {
                this.notificationService.warn(':: Adding unsuccessfully');
              }
            );
          } else {
            this.notificationService.warn(':: Do not have the previous version');
          }
        },
        error1 => {
          this.notificationService.warn(':: New Team unsuccessfully');
        }
      );
    }
  }
}
