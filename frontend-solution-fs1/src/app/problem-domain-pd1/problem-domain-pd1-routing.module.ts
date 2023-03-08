import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FilmDetailComponent } from './components/film-detail/film-detail.component';
import { FilmListComponent } from './components/film-list/film-list.component';

const routes: Routes = [
  { path: '', component: FilmListComponent },
  { path: 'film/:id', component: FilmDetailComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProblemDomainPd1RoutingModule { }
