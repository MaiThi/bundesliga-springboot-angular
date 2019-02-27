import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Team} from '../Model/team';
import {Tournament} from '../Model/tournament';
import {TeamInTournament} from '../Model/team-in-tournament';
import {observableToBeFn} from 'rxjs/internal/testing/TestScheduler';
import {Player} from '../Model/player';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = 'http://localhost:8080/';
  private TEAM_URL = this.BASE_URL + 'teams/api';
  private SAVE_UPDATE_TEAM_URL = this.TEAM_URL + '/auth';
  private ALL_TEAMS_URL = this.TEAM_URL + '/auth/all';
  private FIND_TEAMS_BY_NAME = this.TEAM_URL + '/byContainName/';
  private DELETE_TEAM_URL = this.TEAM_URL + '/auth/';

  private TOURNAMENT_URL = this.BASE_URL + 'tournaments/api';
  private TOP_SEASON_URL = this.TOURNAMENT_URL + '/get-top';
  private SEASON_BY_ID_URL = this.TOURNAMENT_URL + '/byId/';
  private SAVE_UPDATE_SEASON_URL = this.TOURNAMENT_URL + '/auth';

  private TEAM_TOUR_URL = this.BASE_URL + 'teamsinseason/api';
  private GET_BY_ID_TEAM_TOUR_URL = this.TEAM_TOUR_URL + '/byId';
  private SAVE_UPDATE_TEAM_TOUR_URL = this.TEAM_TOUR_URL + '/auth';
  private DELETE_TEAM_TOUR_URL = this.SAVE_UPDATE_TEAM_TOUR_URL + '/';
  private TEAMS_BY_SEASON_ID_URL = this.TEAM_TOUR_URL + '/byTournament/';
  private GET_TEAMTOUR_BY_TEAM_SS_URL = this.TEAM_TOUR_URL + '/byTeamAndTournament/';
  private GET_TEAMTOUR_BY_TEAM_AND_TOUR_ID_RANGE_6_URL = this.TEAM_TOUR_URL + '/byTourIdAndTeamTourId/';

  private PLAYER_URL = this.BASE_URL + 'players/api'
  private All_PLAYERS_URL = this.PLAYER_URL + '/all';
  private SAVE_UPDATE_PLAYER_URL = this.PLAYER_URL + '/auth';

  constructor(private http: HttpClient) { }


  getAllTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.ALL_TEAMS_URL);
  }

  getTopTournament(): Observable<Tournament[]> {
    return this.http.get<Tournament[]>(this.TOP_SEASON_URL);
  }

  getTournametnById(id: number): Observable<Tournament> {
    return this.http.get<Tournament>(this.SEASON_BY_ID_URL + id);
  }

  postTournament(season: Tournament): Observable<Tournament> {
    return this.http.post<Tournament>(this.SAVE_UPDATE_SEASON_URL, season);
  }

  getTeamsByContainName(name: string): Observable<Team[]> {
    return this.http.get<Team[]>(this.FIND_TEAMS_BY_NAME + name);
  }

  getTeamsByTournamentId(id: number): Observable<TeamInTournament[]>  {
    return this.http.get<TeamInTournament[]>(this.TEAMS_BY_SEASON_ID_URL + id);
  }

  getTeamTourByTourIdAndTeamId (tourId: number, teamId: number): Observable<TeamInTournament[]> {
    return this.http.get<TeamInTournament[]>(this.GET_TEAMTOUR_BY_TEAM_AND_TOUR_ID_RANGE_6_URL + tourId + '/' + teamId);
  }

  postTeam(team: Team): Observable<Team> {
    return this.http.post<Team>(this.SAVE_UPDATE_TEAM_URL, team);
  }

  deleteTeam(id: number): Observable<any> {
      return this.http.delete(this.DELETE_TEAM_URL + id);
  }

  getTeamTourById (id: number): Observable<TeamInTournament> {
    return this.http.get<TeamInTournament>(this.GET_BY_ID_TEAM_TOUR_URL + '/' + id);
  }
  getTeamTournamentByTeamAndSeason(teamId: number, tournamentId: number): Observable<TeamInTournament> {
    return this.http.get<TeamInTournament>(this.GET_TEAMTOUR_BY_TEAM_SS_URL + teamId + '/' + tournamentId);
  }

  postTeamInTournament(teamtour: TeamInTournament): Observable<TeamInTournament> {
    return this.http.post<TeamInTournament>(this.SAVE_UPDATE_TEAM_TOUR_URL, teamtour);
  }

  deleteTeamInTournament(id: number): Observable<any> {
    return this.http.delete(this.DELETE_TEAM_TOUR_URL + id);
  }

  getAllPlayers(): Observable<Player[]> {
      return this.http.get<Player[]>(this.All_PLAYERS_URL);
  }
  postPlayer(player: Player): Observable<Player> {
    return this.http.post<Player>(this.SAVE_UPDATE_PLAYER_URL, player);
  }

}
