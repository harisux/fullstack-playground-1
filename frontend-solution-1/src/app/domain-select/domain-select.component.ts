import { Component } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Domain } from './models/domain-models';
import { DomainService } from './services/domain.service';

@Component({
  selector: 'app-domain-select',
  templateUrl: './domain-select.component.html',
  styleUrls: ['./domain-select.component.scss']
})
export class DomainSelectComponent {

  domainList$: Observable<Domain[]> = this.domainService.getDomainList().pipe(
    tap(d => console.log(d)));

  constructor(private domainService: DomainService) {}

}
