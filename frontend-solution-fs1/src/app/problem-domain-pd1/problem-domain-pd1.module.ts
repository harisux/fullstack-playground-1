import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProblemDomainPd1RoutingModule } from './problem-domain-pd1-routing.module';
import { FilmListComponent } from './components/film-list/film-list.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { HttpClientModule } from '@angular/common/http';
import { BackendSelectComponent } from '../backend-select/backend-select.component';

@NgModule({
  declarations: [
    FilmListComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    BackendSelectComponent,
    MatProgressBarModule,
    MatTableModule,
    ProblemDomainPd1RoutingModule
  ]
})
export class ProblemDomainPd1Module { }
