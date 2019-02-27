import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Location} from '../Model/location';
@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private BASE_URL = 'http://localhost:8080/';
  private LOCATION_URL = this.BASE_URL + 'api/locations';
  private SAVE_UPDATE_LOCATION_URL = this.LOCATION_URL;



  constructor(private http: HttpClient) { }

  postLocation (location: Location): Observable<Location> {
    return this.http.post<Location>(this.LOCATION_URL, location);
  }

  putLocation (location: Location): Observable<Location> {
    return this.http.put<Location>(this.LOCATION_URL, location);
  }
}
