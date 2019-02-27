import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PlayerInMatch} from '../Model/player-in-match';

@Injectable({
  providedIn: 'root'
})
export class PlayerInMatchService {
  private BASE_URL = 'http://localhost:8080/';
  private PLAYERS_IN_MATCH_URL = this.BASE_URL + 'play-in-matches/api';
  private POST_PUT_DELETE_PLAYERS_IN_MATCH_URL = this.PLAYERS_IN_MATCH_URL + '/auth';
  private ADD_PLAYERS_INTO_MATCH_WITH_TEAMSS_ID_URL = this.PLAYERS_IN_MATCH_URL + '/auth/addPlayersIntoMatchByTeamId/';
  private GET_PLAYERS_BY_MATCH_ID_AND_TEAMSS_ID_URL = this.PLAYERS_IN_MATCH_URL + '/getByMatchAndTeam/';
  private ADD_PLAYERS_BY_THE_PREVIOUS_MATCH_URL = this.PLAYERS_IN_MATCH_URL + '/auth/addPlayersBasedOnPreviousMatch';

  constructor(private http: HttpClient) { }

  postCreateByMatchAndTeamTournament (matchId: number, teamtourId: number): Observable<PlayerInMatch[]> {
    return this.http.get<PlayerInMatch[]>(this.ADD_PLAYERS_INTO_MATCH_WITH_TEAMSS_ID_URL + matchId + '/' + teamtourId);
  }

  getPlayerInMatchByMatchIdAndTeamTourId (matchId: number, teamtourId: number): Observable<PlayerInMatch[]> {
    return this.http.get<PlayerInMatch[]>(this.GET_PLAYERS_BY_MATCH_ID_AND_TEAMSS_ID_URL + matchId + '/' + teamtourId);
  }

  putPlayerInMatch(playerInMatch: PlayerInMatch): Observable<PlayerInMatch> {
    return this.http.put<PlayerInMatch>(this.POST_PUT_DELETE_PLAYERS_IN_MATCH_URL, playerInMatch);
  }

  deletePlayerInMatchById(playerInMatchId: number): Observable<any> {
    return this.http.delete(this.POST_PUT_DELETE_PLAYERS_IN_MATCH_URL + '/' + playerInMatchId);
  }

  postCreateByThePreviousMatch (matchId: number, currentMatchId: number): Observable<PlayerInMatch[]> {
    return this.http.get<PlayerInMatch[]>(this.ADD_PLAYERS_BY_THE_PREVIOUS_MATCH_URL + '/' + matchId + '/' + currentMatchId);
  }
}
