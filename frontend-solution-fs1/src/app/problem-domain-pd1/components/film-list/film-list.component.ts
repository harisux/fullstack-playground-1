import { ChangeDetectionStrategy, Component, inject, OnInit, ViewChild } from '@angular/core';
import {  map, Observable, tap } from 'rxjs';
import { Film } from '../../models/films';
import { FilmsService } from '../../services/films.service';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FilmListComponent implements OnInit {

  filmDataSource$: Observable<Film[]> | undefined;
  displayedColumns: string[] = ['title', 'name', 'length', 'rental_rate', 'rating'];

  //Services
  filmsService = inject(FilmsService);

  ngOnInit(): void {
    this.filmDataSource$ = this.filmsService.getFilms().pipe(
      tap(films => console.log("films", films)),
      map(filmsData => filmsData.data )
    );
  }

}
