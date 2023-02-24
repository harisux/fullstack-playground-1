import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackendDiscoveryService } from './services/backend-discovery.service';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import { map, Observable, switchMap, tap } from 'rxjs';
import { BackendOption } from './models/backend-option';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-backend-select',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatChipsModule,
    MatListModule,
    MatExpansionModule
  ],
  providers: [ BackendDiscoveryService ],
  templateUrl: './backend-select.component.html',
  styleUrls: ['./backend-select.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BackendSelectComponent implements OnInit {
  selectedDomainId$: Observable<string | null> | undefined;
  backendOptions$: Observable<BackendOption[]> | undefined;

  //Services
  backendDiscovery = inject(BackendDiscoveryService);
  route = inject(ActivatedRoute);

  ngOnInit() {
    this.selectedDomainId$ = this.route.paramMap.pipe(map(params => params.get('id')));
    this.backendOptions$ = this.selectedDomainId$.pipe(
      switchMap(id => {
        return this.backendDiscovery.getBackendOptions(id ? id : '');
      })
      ,tap(b => console.log(b))      
    );
  }

}
