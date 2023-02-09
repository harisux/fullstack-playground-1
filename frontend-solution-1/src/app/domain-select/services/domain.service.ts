import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Domain } from '../models/domain-models';

@Injectable({
  providedIn: 'root'
})
export class DomainService {

  domainListEndpoint: string = 'assets/domain-list.json';

  constructor(private httpClient: HttpClient) { }

  getDomainList(): Observable<Domain[]> {
    return this.httpClient.get<Domain[]>(this.domainListEndpoint);
  }

}
