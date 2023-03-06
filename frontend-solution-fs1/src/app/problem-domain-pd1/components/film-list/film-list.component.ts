import { AfterViewInit, ChangeDetectionStrategy, Component, inject, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {  delay, map, Observable, tap } from 'rxjs';
import { Film } from '../../models/films';
import { FilmsService } from '../../services/films.service';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FilmListComponent implements OnInit {

  filmDataSource$: Observable<MatTableDataSource<Film>> | undefined;
  displayedColumns: string[] = ['title', 'name', 'length', 'rental_rate', 'rating'];

  //Services
  filmsService = inject(FilmsService);

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  ngOnInit(): void {
    this.filmDataSource$ = this.filmsService.getFilms().pipe(
      //delay(500),
      tap(films => console.log("films", films)),
      map(filmsData => { 
        let filmsDataSource = new MatTableDataSource(filmsData.data);
        console.log('paginator', this.paginator);
        filmsDataSource.paginator = this.paginator ? this.paginator : null;
        return filmsDataSource;
      })
    );
  }
  
}
