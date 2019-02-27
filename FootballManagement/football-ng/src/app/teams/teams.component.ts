import { Component, OnInit } from '@angular/core';
import {Tournament} from '../Model/tournament';
import {Team} from '../Model/team';
import {ApiServiceService} from '../ApiService/api-service.service';
import {TeamInTournament} from '../Model/team-in-tournament';
import {forEach} from '@angular/router/src/utils/collection';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {EditMatchComponent} from '../schedule-admin/edit-match/edit-match.component';
import {AddLocationComponent} from './add-location/add-location.component';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {
  isLoggedIn =  false;
  tournaments: Tournament[] = [];

  teamsSeason: TeamInTournament[] = [];
  selectedSeason: number;
  allTeam: number;
  findedTeam: Team[] = [];
  findedTeamTour: TeamInTournament;
  newSeasonCheck: number;
  titleTeamComponent: string;

  teamTourNews: TeamInTournament[] = [];
  currentSeason: Tournament;
  constructor(private apiService: ApiServiceService,
              private dialog: MatDialog,
              private tokenStorage: TokenStorageService) { }

  ngOnInit() {
   this.getTopTournament();
   this.allTeam = 0;
   this.newSeasonCheck = 0;
   this.titleTeamComponent = 'Teams';
   if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
   }

  }

   getTopTournament() {
      this.apiService.getTopTournament().subscribe(
        res => {
           this.tournaments = res;
           this.selectedSeason = this.tournaments[0].id;
           this.getTeamBySeason(this.selectedSeason);
        },
        error1 => {
          alert('The error has occurred');
        }
      );
  }


  getTeamBySeason(ss: number) {
      this.apiService.getTeamsByTournamentId(ss).subscribe(
        res => {
          this.teamsSeason = res;
        },
        error1 => {
          alert('An error has occurred');
        }
      );
  }
  selectedSeasonChange(event: any) {
    this.allTeam = 0;
    this.selectedSeason = event.target.value;
    this.newSeasonCheck = 0;
    this.titleTeamComponent = 'Teams';
    this.getTeamBySeason(this.selectedSeason);
  }

  createNewTeam() {
    if (this.allTeam === 1) {

    } else {
      const newTeam: TeamInTournament = {
        id: null,
        teamName: 'New Temp Team',
        logoTeam: 'temp-icon.png',
        teamId: null,
        tournamentName: '',
        tournamentId: this.selectedSeason,
        outJScore: 0,
        homeJScore: 0,
        totalScore: 0,
        status: 1,
        color: 'red',
        locationName: 'New Location',
        mW: 0,
        mD: 0,
        mL: 0,
        gF: 0,
        gA: 0,
        diff: 0,
        weekNo: ''
      };
      this.teamsSeason.splice(0, 0, newTeam);
      // TO DO IMPLEMENT CREATE TEAM IN SEASON
    }
  }

  findingTeam(teamTour: TeamInTournament) {
      this.apiService.getTeamsByContainName(teamTour.teamName).subscribe(
        res => {
          this.findedTeam = res;
          teamTour.status = 0;
          const indexOf = this.teamsSeason.indexOf(teamTour);
          if ( this.findedTeam != null &&  this.findedTeam.length !== 0) {
              this.apiService.getTeamTournamentByTeamAndSeason(this.findedTeam[0].id, teamTour.tournamentId).subscribe(
                res1 => {
                    this.findedTeamTour = res1;
                    if (this.findedTeamTour == null) {
                       // TO DO edit infor and add team into season
                      teamTour.teamId = this.findedTeam[0].id;
                      this.apiService.postTeamInTournament(teamTour).subscribe(
                        res2 => {
                            this.teamsSeason[indexOf] = res2;
                        },
                        error3 => {
                          alert('An error has occured when adding new Team into this Season 2');
                        }
                      );
                    } else {
                      this.teamsSeason.splice(indexOf, 1);
                      alert('Already exist team in this season');
                    }
                },
                error2 => {}
              );
          } else {
            // TODO add team and add team into this season.
            const newTeam = {
              id: null,
              name: teamTour.teamName,
              logoTeam: teamTour.logoTeam,
              color: 'red'
            };
            this.apiService.postTeam(newTeam).subscribe(
              resTeam => {
                teamTour.teamId = resTeam.id;
                const dialogConfig = new MatDialogConfig();
                dialogConfig.disableClose = true;
                dialogConfig.autoFocus = true;
                dialogConfig.width = '70%';
                dialogConfig.data = {
                  teamId: teamTour.teamId
                }
                this.dialog.open(AddLocationComponent, dialogConfig).afterClosed().subscribe(
                  data => { this.apiService.postTeamInTournament(teamTour).subscribe(
                    resTeamTour => {
                      this.teamsSeason[indexOf] = resTeamTour;
                      // Open dialog for adding new Location.
                      // Because 1 team have 1 their own stadium
                    },
                    errorTeamTour => {
                      alert('An error has occured when adding new Team into this Season 1');
                    }
                  );
                  }
                );
              },
              errorTeam => {
                alert('An error has occured when adding new Team');
              }

          );
          }
        },
        error1 => {
          alert('An error has occured');
        }
      );
  }

  deleteTeamInTournament(teamTour: TeamInTournament) {
    this.apiService.deleteTeamInTournament(teamTour.id).subscribe(
      res => {
        const indexOf = this.teamsSeason.indexOf(teamTour);
        this.teamsSeason.splice(indexOf, 1);
      },
      error1 => {}
    );
  }

  updateTeamSeason(teamTour: TeamInTournament) {
    const updateTeam: Team = {
      id: teamTour.teamId,
      name: teamTour.teamName,
      logoTeam: teamTour.logoTeam,
      color: ''
    };
   // this.updateTeam(updateTeam);

  }


  createNewSeason() {
    const newSeason = {
      id: -1,
      name: 'New Season',
      startedDate: null,
      endedDate: null,
      nbTeams: 18
    };
    this.apiService.postTournament(newSeason).subscribe(
        res => {
          const oldSeasonId = this.tournaments[0].id;
          this.tournaments.splice(0, 0, res);
          this.currentSeason = this.tournaments[0];
          this.selectedSeason = this.currentSeason.id;
          this.newSeasonCheck = 1;
          this.titleTeamComponent = 'Teams from the previous season';
          // Reset all teams in season, prepare having new team in the new season
          this.teamsSeason = [];
          this.apiService.getTeamsByTournamentId(oldSeasonId).subscribe(
            resGetTeamTour => {
               this.teamTourNews = resGetTeamTour;
              this.teamTourNews.forEach(tt => {
                  this.createTTWithParameter(tt.teamName, tt.logoTeam, tt.color,  tt.locationName, tt.teamId, this.currentSeason.name,
                                            this.currentSeason.id, 0, 0, 0);
              });
            },
            errorGetTeamTours => {}
          );
        },
      error1 => {
        alert('Could not add new season');
      }
    );
  }

  createTTWithParameter ( teamName: string, logoTeam: string, color: string,
                          locationName: string, teamId: number, tournamentName: string, tournamentId: number,
                          outJScore: number, homeJScore: number, totalScore: number) {
      const newTT: TeamInTournament = {
        id: null,
        teamName: teamName,
        logoTeam: logoTeam,
        teamId: teamId,
        tournamentName: tournamentName,
        tournamentId: tournamentId,
        outJScore: outJScore,
        homeJScore: homeJScore,
        totalScore: totalScore,
        status: 0,
        color: color,
        locationName: locationName,
        mW: 0,
        mD: 0,
        mL: 0,
        gF: 0,
        gA: 0,
        diff: 0,
        weekNo: ''
      };
      this.apiService.postTeamInTournament(newTT).subscribe(
        res => {
            this.teamsSeason.push(res);
        },
        error1 => {}
      );
  }
  updateSeason (season: Tournament) {
    this.apiService.postTournament(season).subscribe(
      res => {},
      error1 => {}
    );
  }

  selectedAllTeam() {
    this.allTeam = 1;
  }
}
