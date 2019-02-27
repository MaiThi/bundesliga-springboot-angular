import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MatchDetail} from '../Model/match-detail';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class MatchDetailsService {
  private BASE_URL = 'http://localhost:8080/';
  private MATCH_DETAILS_URL = this.BASE_URL + 'match-details/api';
  private POST_MATCH_DETAIL_URL = this.MATCH_DETAILS_URL + '/auth';
  private GET_ALL_DETAILS_BY_MATCH_ID_URL = this.MATCH_DETAILS_URL + '/getByMatch';
  private GET_DETAIL_BY_ID_URL = this.MATCH_DETAILS_URL + '/getById/';

  constructor(private http: HttpClient) { }

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    happenedDate: new FormControl(),
    playInMatchId: new FormControl( 1, Validators.required),
    playerId: new FormControl(''),
    playerName: new FormControl(),
    teamId: new FormControl(''),
    teamName: new FormControl(''),
    cardReceived: new FormControl(0),
    action_time: new FormControl(0, Validators.required),
    is_goal_from_11m: new FormControl(),
    is_the_bad_goal: new FormControl(),
    is_Goal: new FormControl(),
    is_MoveOut: new FormControl(),
    is_MoveIn: new FormControl()
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null ,
      happenedDate: null,
      playInMatchId: 0,
      playerId: '',
      playerName: '',
      teamId: '',
      teamName: '',
      cardReceived: '',
      action_time: 0,
      is_goal_from_11m: false,
      is_the_bad_goal: false,
      is_Goal: false,
      is_MoveOut: false,
      is_MoveIn: false
    });
  }

  populateMatchDetail (row: MatchDetail) {
    this.form.setValue(row);
  }

  deteleDetailById (matchDetailId: number): Observable<any> {
    return this.http.delete(this.POST_MATCH_DETAIL_URL + '/' + matchDetailId);
  }

  getDetailsByMatchId (matchId: number): Observable<MatchDetail[]> {
    return this.http.get<MatchDetail[]>(this.GET_ALL_DETAILS_BY_MATCH_ID_URL + '/' + matchId);
  }

  addMatchDetail (matchDetail: MatchDetail): Observable<MatchDetail> {
    return this.http.post<MatchDetail>(this.POST_MATCH_DETAIL_URL, matchDetail);
  }

  updateMatchDetail (matchDetail: MatchDetail): Observable<MatchDetail> {
    return this.http.put<MatchDetail>(this.POST_MATCH_DETAIL_URL, matchDetail);
  }

  getById (id: number): Observable<MatchDetail> {
    return this.http.get<MatchDetail>(this.GET_DETAIL_BY_ID_URL + id);
  }
}
