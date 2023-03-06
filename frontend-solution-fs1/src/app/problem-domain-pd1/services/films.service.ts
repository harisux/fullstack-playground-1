import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { BackendDiscoveryService } from 'src/app/backend-select/services/backend-discovery.service';
import { Film, FilmsData } from '../models/films';

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

}
