import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Domain } from './models/domain-models';
import { DomainService } from './services/domain.service';

import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-domain-select',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatChipsModule
  ],
  providers: [
    DomainService
  ],
  templateUrl: './domain-select.component.html',
  styleUrls: ['./domain-select.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DomainSelectComponent implements OnInit {

  domainList$: Observable<Domain[]> | undefined;

  //Services
  domainService = inject(DomainService);
  router = inject(Router);

  ngOnInit(): void {
    this.domainList$ = this.domainService.getDomainList();
  }

  goToDomain(domain: Domain): void {
    this.router.navigate(['/domain/' + domain.id]);
  }

}
