import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ObjectUnsubscribedError, Observable} from 'rxjs';
import {Match} from '../Model/match';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Player} from '../Model/player';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private BASE_URL = 'http://localhost:8080/';
  private MATCHES_URL = this.BASE_URL + 'matches/api';
  private MATCH_BY_ID_URL = this.MATCHES_URL + '/getById/';
  private CHECK_ALREADY_CREATED_MATCH_URL = this.MATCHES_URL + '/checkedExistingMatch/';
  private CREATE_MATCHES_BY_TOURNAMENT_URL = this.MATCHES_URL + '/auth/initialize/';
  private GET_MATCHES_BY_TOURNAMENT_URL = this.MATCHES_URL + '/getMatchesByTournamentId/';
  private GET_VISIT_TEAM_NOT_YET_ARRANGE_TIME = this.MATCHES_URL + '/getVisitTeamsByHomeTeam/';
  private GET_MATCHES_IN_DAY_NO_URL = this.MATCHES_URL + '/getMatchesByTournamentAndDayNo/';
  private GET_MATCH_BY_HOME_TEAM_AND_VISIT_TEAM_URL = this.MATCHES_URL + '/getMatchByHomeAndVisitTeam/';
  private CALCULATE_SCORE_FOR_TEAM = this.MATCHES_URL + '/updateScoreTeamsInMatch/';
  private UPDATE_CREATE_MATCH_URL = this.MATCHES_URL + '/auth';
  private GET_MATCHES_ALREADY_ASSIGN_PLAYERS_URL = this.MATCHES_URL + '/auth/getListMatchesAlreadyAssignPlayers';
  private GET_MATCHES_BY_TEAM_TOUR_URL = this.MATCHES_URL + '/getListMatchesByTeamTour/';


  constructor(private http: HttpClient) { }
  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    tournamentId: new FormControl(),
    homeTeamId: new FormControl(1),
    homeTeamName:  new FormControl(''),
    logoHome: new FormControl(''),
    visitTeamId: new FormControl(1),
    visitTeamName: new FormControl('', Validators.required),
    logoVisit: new FormControl(''),
    happenedDate: new FormControl(new Date()),
    homeGoals: new FormControl(999),
    visitGoals: new FormControl(999),
    locationId: new FormControl(),
    locationName: new FormControl(0),
    locationImage: new FormControl(''),
    dayNo: new FormControl(''),
    description: new FormControl(' '),
    finishDetails: new FormControl(''),
    finishPlayer: new FormControl('')
  });

  initializeFormGroup(tournamentId: number, dayNo: string) {
    this.form.setValue({
      id: null ,
      homeTeamId: 1,
      tournamentId: tournamentId,
      homeTeamName: '',
      visitTeamId: 999,
      visitTeamName: '',
      happenedDate: new Date(),
      homeGoals: 999,
      visitGoals: 999,
      locationId: 1,
      locationName: '',
      dayNo: dayNo,
      description: '',
      finishDetails: '',
      finishPlayer: '',
      logoHome: '',
      logoVisit: '',
      locationImage: ''
    });
  }

  populateForm(match: Match) {
    this.form.setValue(match);
  }

  getMatchById (matchId: number): Observable<Match> {
    return this.http.get<Match>(this.MATCH_BY_ID_URL + matchId);
  }

  getCheckAlreadyCreatedMatches(tournamentId: number): Observable<number> {
    return this.http.get<number>(this.CHECK_ALREADY_CREATED_MATCH_URL + tournamentId);
  }

  initializeMatchesByTournamentId (tournamentId: number): Observable<Match[]> {
      return this.http.get<Match[]>(this.CREATE_MATCHES_BY_TOURNAMENT_URL + tournamentId);
  }

  getMatchesByTournamentId (tournamentId: number): Observable<Match[]> {
    return this.http.get<Match[]>(this.GET_MATCHES_BY_TOURNAMENT_URL + tournamentId);
  }

  getMatchesByHomeTeamAndVisitteam (homeTeamId: number, visitTeamId: number): Observable<Match> {
    return this.http.get<Match>(this.GET_MATCH_BY_HOME_TEAM_AND_VISIT_TEAM_URL + homeTeamId + '/' + visitTeamId);
  }

  getVisitTeamNotYetArrangedTimePlaying (homeTeamId: number, type: number): Observable<Match[]> {
    return this.http.get<Match[]>(this.GET_VISIT_TEAM_NOT_YET_ARRANGE_TIME + homeTeamId + '/' + type);
  }

  getTeamByDayNoAndTournament (tournamentId: number, dayNo: string): Observable<Match[]> {
    return this.http.get<Match[]>(this.GET_MATCHES_IN_DAY_NO_URL + tournamentId + '/' + dayNo);
  }

  updateMatches(match: Match): Observable<Match> {
    return this.http.put<Match>(this.UPDATE_CREATE_MATCH_URL, match);
  }

  calculateScoreForTeams (matchId: number): Observable<any> {
    return this.http.get<any>(this.CALCULATE_SCORE_FOR_TEAM + matchId);
  }

  getMatchesAlreadyAssignPlayers(teamTourID: number): Observable<Match[]> {
    return this.http.get<Match[]>(this.GET_MATCHES_ALREADY_ASSIGN_PLAYERS_URL + '/' + teamTourID);
  }

  getMatchesByTeamTourForHomeAndVisitTeam(teamTourid: number): Observable<Match[]> {
    return this.http.get<Match[]> (this.GET_MATCHES_BY_TEAM_TOUR_URL + teamTourid);
  }
}
