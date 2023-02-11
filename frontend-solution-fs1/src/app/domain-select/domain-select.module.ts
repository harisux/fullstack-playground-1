import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DomainSelectRoutingModule } from './domain-select-routing.module';
import { DomainSelectComponent } from './domain-select.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
import { DomainService } from './services/domain.service';

@NgModule({
  declarations: [
    DomainSelectComponent
  ],
  imports: [
    CommonModule,
    DomainSelectRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatChipsModule
  ],
  providers: [
    DomainService
  ]
})
export class DomainSelectModule { }
