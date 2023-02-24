import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProblemDomainPd1RoutingModule } from './problem-domain-pd1-routing.module';
import { FilmListComponent } from './components/film-list/film-list.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { FilmsService } from './services/films.service';
import { HttpClientModule } from '@angular/common/http';
import { BackendDiscoveryService } from '../backend-select/services/backend-discovery.service';

@NgModule({
  declarations: [
    FilmListComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    MatProgressBarModule,
    MatTableModule,
    ProblemDomainPd1RoutingModule
  ],
  providers: [
    FilmsService,
    BackendDiscoveryService
  ]
})
export class ProblemDomainPd1Module { }
