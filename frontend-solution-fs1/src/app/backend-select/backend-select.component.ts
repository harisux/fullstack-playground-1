import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackendDiscoveryService } from './services/backend-discovery.service';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
import { MatListModule } from '@angular/material/list';
import { Observable, tap } from 'rxjs';
import { BackendOption } from './models/backend-option';
import { Domain } from '../domain-select/models/domain-models';

@Component({
  selector: 'app-backend-select',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatChipsModule,
    MatListModule
  ],
  providers: [ BackendDiscoveryService ],
  templateUrl: './backend-select.component.html',
  styleUrls: ['./backend-select.component.scss']
})
export class BackendSelectComponent implements OnInit {

  selectedDomain: Domain | undefined;
  backendOptions$: Observable<BackendOption[]> | undefined;
        
  constructor(private backendDiscovery: BackendDiscoveryService) {}

  ngOnInit() {
    this.selectedDomain = {} as Domain;
    this.backendOptions$ = this.backendDiscovery.getBackendOptions(this.selectedDomain.id).pipe(
      tap(b => console.log(b))
    );
  }

}
