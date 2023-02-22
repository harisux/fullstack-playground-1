import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProblemDomainPd1RoutingModule } from './problem-domain-pd1-routing.module';
import { FilmListComponent } from './components/film-list/film-list.component';


@NgModule({
  declarations: [
    FilmListComponent
  ],
  imports: [
    CommonModule,
    ProblemDomainPd1RoutingModule
  ]
})
export class ProblemDomainPd1Module { }
