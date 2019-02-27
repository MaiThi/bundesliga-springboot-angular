import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Player} from '../../Model/player';
import {MatSort, MatTableDataSource, MatIcon, MatPaginator} from '@angular/material';
import {ApiServiceService} from '../../ApiService/api-service.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {PlayerComponent} from '../player/player.component';
import {PlayerService} from '../../ApiService/player.service';

@Component({
  selector: 'app-players-info',
  templateUrl: './players-info.component.html',
  styleUrls: ['./players-info.component.css']
})
export class PlayersInfoComponent implements OnInit {

  listPlayers: MatTableDataSource<any>;
  allPlayers: Player[] = [];
  displayedColumns: string[] = ['image', 'firstName', 'lastName', 'dateOfBirth', 'nationality', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  constructor(private playerService: PlayerService,  private dialog: MatDialog) { }
  ngOnInit() {
    this.getAllPlayers();
    this.searchKey = ' ';
  }
  getAllPlayers() {
    this.playerService.getAllPlayers().subscribe(
      res => {
        this.allPlayers = res;
        this.filterValueIntoMatSource(this.allPlayers);
      },
      error1 => {}
    );
  }
  filterValueIntoMatSource(players: Player[]) {
    this.listPlayers = new MatTableDataSource(players);
    this.listPlayers.sort = this.sort;
    this.listPlayers.paginator = this.paginator;
    this.listPlayers.filterPredicate =
      (data: Player, filter: string) => (data.lastName !== null) ? data.lastName.toLowerCase().indexOf(filter) !== -1 : false;
    if (this.listPlayers.filteredData.length !== 0 ) {
      this.listPlayers.filterPredicate =
        (data: Player, filter: string) => (data.firstName !== null) ? data.firstName.toLowerCase().indexOf(filter) !== -1 : false;
    }
  }
  onSearchClear() {
   // alert(this.searchKey);
    this.applyFilter();
    this.searchKey = ' ';
  }

  applyFilter() {
    this.listPlayers.filter = this.searchKey.trim().toLowerCase();
  }

  onCreate() {
    this.playerService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    const dialogRef = this.dialog.open(PlayerComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => this.refreshData(data)
    );
  }

  refreshData(data: Player) {
    if (data != null) {
      this.playerService.getAllPlayers().subscribe(
        res => {
          this.allPlayers = res;
          this.filterValueIntoMatSource(this.allPlayers);
        },
        error1 => {

        }
      );
    }
  }

  onEdit(row) {
    this.playerService.populateForm(row);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    this.dialog.open (PlayerComponent, dialogConfig).afterClosed().subscribe (
      data => this.refreshData(data)
    );
  }

  onDelete(row) {
    this.playerService.populateForm(row);
    if (confirm('Are you sure you want to delete the information of this player?')) {
        let player: Player = this.playerService.form.value;
        this.playerService.deletePlayer(player.id).subscribe(
          res => {
            let indexOf = this.allPlayers.indexOf(player);
            this.allPlayers.splice(indexOf, 1);
            this.filterValueIntoMatSource(this.allPlayers);
          },
          error1 => {}
        );
    }
  }
}
