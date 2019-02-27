import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private BASE_URL = 'http://localhost:8080/api/images';
  private POST_URL = this.BASE_URL + '/post';
  private ALL_FILES_URL = this.BASE_URL + '/getallfiles';

  constructor(private http: HttpClient) { }

  pushFileToStorage(file: File): Observable<String> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);

    return this.http.post<String>(this.POST_URL, formdata);
  }

  getFiles(): Observable<string[]> {
    return this.http.get<string[]>(this.ALL_FILES_URL);
  }
}
