import { ChangeDetectionStrategy, Component } from '@angular/core';
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
export class DomainSelectComponent {

  domainList$: Observable<Domain[]> = this.domainService.getDomainList();

  constructor(private domainService: DomainService, private router: Router) {}
  
  goToDomain(domain: Domain): void {
    console.log('hi');
    this.router.navigate(['/domain/' + domain.id]);
  }

}
