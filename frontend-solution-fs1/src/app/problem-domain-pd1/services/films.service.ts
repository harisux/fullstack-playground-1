import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { BackendDiscoveryService } from 'src/app/backend-select/services/backend-discovery.service';
import { Film } from '../models/films';

@Injectable({
  providedIn: 'root'
})
export class FilmsService {
  
  //Services
  private httpClient = inject(HttpClient);
  private backendDiscovery = inject(BackendDiscoveryService);
  
  public getFilms(): Observable<Film[]> {
      return this.backendDiscovery.getSelectedBackendBaseUrl().pipe(
        switchMap(url => this.httpClient.get<Film[]>(`${url}api/v1/films?limit=5`))
      );
  }

}
