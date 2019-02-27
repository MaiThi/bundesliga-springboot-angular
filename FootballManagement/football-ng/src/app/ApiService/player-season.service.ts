import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ObjectUnsubscribedError, Observable} from 'rxjs';
import {PlayerInTeamTournament} from '../Model/player-in-team-tournament';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatTableDataSource} from '@angular/material';
import {TeamInTournament} from '../Model/team-in-tournament';
import {Player} from '../Model/player';

@Injectable({
  providedIn: 'root'
})
export class PlayerSeasonService {

  private BASE_URL = 'http://localhost:8080/';
  private PLAY_IN_SEASON_URL = this.BASE_URL + 'play-in-tournaments/api';
  private PLAYERS_BY_TEAM_TOUR_URL = this.PLAY_IN_SEASON_URL + '/byTeamTourId/';
  private POST_PLAYER_BY_TEAM_TOUR_URL = this.PLAY_IN_SEASON_URL + '/auth';
  private DELETE_PLAYERS_BY_ID_URL = this.PLAY_IN_SEASON_URL + '/auth/';
  private ADD_MULTIPLE_PLAYERS_INTO_SEASON = this.PLAY_IN_SEASON_URL + '/auth/addMultiple/';
  private GET_PLAYERS_FOR_ADDING_INTO_SEASON_URL = this.PLAY_IN_SEASON_URL + '/getPlayerIdsByTeamTourForAdding';

  private TEAM_TOUR_URL = this.BASE_URL + 'teamsinseason/api';
  private TEAM_TOUR_BY_ID_URL = this.TEAM_TOUR_URL + '/byTeamIdTop/';


  constructor(private http: HttpClient) { }
  formPlayerSeason: FormGroup = new FormGroup({
    id: new FormControl(null),
    teamTourId: new FormControl(0),
    teamName: new FormControl(''),
    tournamentName: new FormControl(''),
    playerId: new FormControl(null, Validators.required),
    playerName: new FormControl(''),
    nbClothes: new FormControl(0, Validators.required),
    nationality: new FormControl(0),
    totalRedCards: new FormControl(0),
    totalYellowCards: new FormControl(0),
    totalGoals: new FormControl(0),
    totalMatchesJoined: new FormControl(0),
    dob: new FormControl(new Date())
  });

  initializeFormGroup() {
    this.formPlayerSeason.setValue({
      id: null ,
      teamTourId: null,
      teamName: '',
      tournamentName: '',
      playerId: null,
      playerName: '',
      nbClothes: 0,
      nationality: '',
      totalRedCards: 0,
      totalYellowCards: 0,
      totalGoals: 0,
      totalMatchesJoined: 0,
      dob: new Date(),
    });
  }

  getPlayersByTeamTourId (teamTourId: number): Observable<PlayerInTeamTournament[]> {
    return this.http.get<PlayerInTeamTournament[]>(this.PLAYERS_BY_TEAM_TOUR_URL + teamTourId);
  }
  populateForm(player: PlayerInTeamTournament) {
    this.formPlayerSeason.setValue(player);
  }

  postPlayer(playerTour: PlayerInTeamTournament): Observable<any> {
    return this.http.post(this.POST_PLAYER_BY_TEAM_TOUR_URL, playerTour);
  }

  putPlayer (playerTour: PlayerInTeamTournament): Observable<PlayerInTeamTournament> {
    return this.http.put<PlayerInTeamTournament>(this.POST_PLAYER_BY_TEAM_TOUR_URL, playerTour);
  }

  deletePlayerById (playerTour: number): Observable<any> {
    return this.http.delete(this.DELETE_PLAYERS_BY_ID_URL + playerTour);
  }

  getTopSeasonOfTeam(teamId: number): Observable<TeamInTournament[]> {
    return this.http.get<TeamInTournament[]>(this.TEAM_TOUR_BY_ID_URL + teamId);
  }

  addPlayersInSeason(teamTourOldId: number, teamTourNewId: number): Observable<PlayerInTeamTournament[]> {
      return this.http.get<PlayerInTeamTournament[]>(this.ADD_MULTIPLE_PLAYERS_INTO_SEASON + teamTourOldId + '/' + teamTourNewId );
  }

  getPlayersForAdding(teamTourId: number): Observable<Player[]> {
    return this.http.get<Player[]>(this.GET_PLAYERS_FOR_ADDING_INTO_SEASON_URL + '/' + teamTourId);
  }
}
