import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProblemDomainPd1RoutingModule } from './problem-domain-pd1-routing.module';
import { FilmListComponent } from './components/film-list/film-list.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { HttpClientModule } from '@angular/common/http';
import { BackendSelectComponent } from '../backend-select/backend-select.component';
import { FilmDetailComponent } from './components/film-detail/film-detail.component';

@NgModule({
  declarations: [
    FilmListComponent,
    FilmDetailComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    BackendSelectComponent,
    MatProgressBarModule,
    MatTableModule,
    MatPaginatorModule,
    MatCardModule,
    ProblemDomainPd1RoutingModule
  ]
})
export class ProblemDomainPd1Module { }
