import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Application, BackendDiscovery } from '../models/backend-discovery';
import { BackendOption, Detail } from '../models/backend-option';

@Injectable({
  providedIn: 'root'
})
export class BackendDiscoveryService {

  discoveryUrl: string = "http://localhost:8761/eureka/v2/apps";
  
  readonly TAGS_SEPARATOR = ','; 
  readonly DETAILS_SEPARATOR = '|';
  readonly DETAILS_KEY_VALUE_SEPARATOR = ':';
  readonly DETAILS_VALUE_LIST_SEPARATOR = ',';

  constructor(private httpClient: HttpClient) { }

  getBackendOptions(domainId: string): Observable<BackendOption[]> {
    return this.httpClient.get<BackendDiscovery>(this.discoveryUrl).pipe(
      map(app => this.mapApplicationToBackendOptions(app))
    );
  }

  private mapApplicationToBackendOptions(discoveryResp: BackendDiscovery): BackendOption[] {
    let backendOptions: BackendOption[] = [];
    discoveryResp.applications.application[0].instance.forEach(inst => {
      let backendOpt: BackendOption = {
        baseUrl: inst.homePageUrl,
        id: inst.app,
        title: inst.metadata.title,
        problemDomainId: inst.metadata.problemDomainId,
        summary: inst.metadata.summary,
        tags: this.assembleTags(inst.metadata.tags),
        details: this.assembleDetails(inst.metadata.detailsList)
      };
      backendOptions.push(backendOpt);
    });
    return backendOptions;
  }

  private assembleTags(tagsStr: string): string[] {
    let tags: string[] = [];
    tagsStr.split(this.TAGS_SEPARATOR).forEach(t => tags.push(t.trim()));
    return tags;
  }

  assembleDetails(detailList: string): Detail[] {
    let details: Detail[] = [];
    detailList.split(this.DETAILS_SEPARATOR).forEach(d => {
      let dKey = d.split(this.DETAILS_KEY_VALUE_SEPARATOR)[0];
      let dValue = d.split(this.DETAILS_KEY_VALUE_SEPARATOR)[1];
      let values: string[] = [];
      dValue.split(this.DETAILS_VALUE_LIST_SEPARATOR).forEach(v => values.push(v.trim()))
      details.push({ title: dKey, values: values });
    });
    return details;
  }

}
