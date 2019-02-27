import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {Player} from '../Model/player';
import {MatTableDataSource} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private BASE_URL = 'http://localhost:8080/';
  private PLAYER_URL = this.BASE_URL + 'players/api'
  private All_PLAYERS_URL = this.PLAYER_URL + '/all';
  private SAVE_UPDATE_PLAYER_URL = this.PLAYER_URL + '/auth';
  private DELETE_PLAYER_URL = this.SAVE_UPDATE_PLAYER_URL + '/';
  private GET_PLAYER_BY_ID_URL = this.PLAYER_URL + '/byId/';
  constructor(private http: HttpClient) { }

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    image: new FormControl('user.png'),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    dateOfBirth: new FormControl(new Date(), Validators.required),
    nationality: new FormControl(''),
    height: new FormControl(0),
    weight: new FormControl(0)
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null ,
      image: 'user.png',
      firstName: '',
      lastName: '',
      dateOfBirth: null,
      nationality: '',
      height: 0,
      weight: 0
    });
  }

  getAllPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(this.All_PLAYERS_URL);
  }
  postPlayer(player: Player): Observable<Player> {
    return this.http.post<Player>(this.SAVE_UPDATE_PLAYER_URL, player);
  }
  putPlayer(player: Player): Observable<Player> {
    return this.http.put<Player>(this.SAVE_UPDATE_PLAYER_URL, player);
  }
  populateForm(player: Player) {
    this.form.setValue(player);
  }

  deletePlayer (id: number): Observable<any> {
    return this.http.delete(this.DELETE_PLAYER_URL + id) ;
  }
  getPlayerById(id: number): Observable<Player> {
    return this.http.get<Player>(this.GET_PLAYER_BY_ID_URL + id);
  }
}
