import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { pipe, map, Observable, Subject, BehaviorSubject, tap } from 'rxjs';
import { Application, BackendDiscovery } from '../models/backend-discovery';
import { BackendOption, Detail } from '../models/backend-option';

@Injectable({
  providedIn: 'root'
})
export class BackendDiscoveryService {

  private discoveryUrl: string = "http://localhost:8761/eureka/v2/apps";
  private selectedBackend$: BehaviorSubject<BackendOption> 
                              = new BehaviorSubject({} as BackendOption);
  
  readonly TAGS_SEPARATOR = ','; 
  readonly DETAILS_SEPARATOR = '|';
  readonly DETAILS_KEY_VALUE_SEPARATOR = ':';
  readonly DETAILS_VALUE_LIST_SEPARATOR = ',';

  //Services
  private httpClient = inject(HttpClient);

  public getBackendOptions(domainId: string): Observable<BackendOption[]> {
    return this.httpClient.get<BackendDiscovery>(this.discoveryUrl).pipe(
      map(app => this.mapApplicationToBackendOptions(app))
    );
  }

  private mapApplicationToBackendOptions(discoveryResp: BackendDiscovery): BackendOption[] {
    let backendOptions: BackendOption[] = [];
    discoveryResp.applications.application.forEach(app => {
      let backendOpt: BackendOption = {
        baseUrl: app.instance[0].homePageUrl,
        id: app.instance[0].app,
        title: app.instance[0].metadata.title,
        problemDomainId: app.instance[0].metadata.problemDomainId,
        summary: app.instance[0].metadata.summary,
        tags: this.assembleTags(app.instance[0].metadata.tags),
        source: app.instance[0].metadata.sourceLink,
        details: this.assembleDetails(app.instance[0].metadata.detailsList)
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

  private assembleDetails(detailList: string): Detail[] {
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

  public selectBackend(backendOpt: BackendOption): void {
    this.selectedBackend$.next(backendOpt);
  }

  public getSelectedBackendBaseUrl(): Observable<string> {
    return this.selectedBackend$.asObservable().pipe(
      map(opt => opt.baseUrl)
    );
  }

}
