import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable, switchMap } from 'rxjs';
import { BackendDiscoveryService } from 'src/app/backend-select/services/backend-discovery.service';
import { Film, FilmsData, Language, LanguagesData } from '../models/films';

@Injectable({
  providedIn: 'root'
})
export class FilmsService {

  //Services
  private httpClient = inject(HttpClient);
  private backendDiscovery = inject(BackendDiscoveryService);
  
  public getFilms(pageSize: number, pageIndex: number): Observable<FilmsData> {
      const offset = pageIndex * pageSize;
      const limit = pageSize;
      return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
        switchMap(url => this.httpClient.get<FilmsData>(
            `${url}api/v1/films?offset=${offset}&limit=${limit}`))
      );
  }

  public getFilm(filmId: string): Observable<Film> {
    return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
      switchMap(url => this.httpClient.get<Film>(`${url}api/v1/films/${filmId}`))
    );
  }

  public getLanguages(): Observable<Language[]> {
    return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
      switchMap(url => this.httpClient.get<LanguagesData>(`${url}api/v1/languages`)),
      map(langData => langData.data)
    );
  }

  public updateFilm(film: Film): Observable<any> {
    return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
      switchMap(url => this.httpClient.put<Film>(`${url}api/v1/films`, film))
    ); 
  }

  public addFilm(film: Film): Observable<any> {
    return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
      switchMap(url => this.httpClient.post<Film>(`${url}api/v1/films`, film))
    ); 
  }

  public deleteFilm(filmId: string): Observable<any> {
    return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
      switchMap(url => this.httpClient.delete<any>(`${url}api/v1/films/${filmId}`))
    );
  }

}
