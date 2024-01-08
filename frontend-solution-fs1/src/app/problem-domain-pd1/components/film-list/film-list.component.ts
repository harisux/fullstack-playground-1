import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import {  BehaviorSubject, delay, first, map, mergeWith, Observable, of, startWith, Subject, switchMap, take, tap } from 'rxjs';
import { Film, FilmsData } from '../../models/films';
import { FilmsService } from '../../services/films.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDeleteDialogComponent } from './confirm-delete-dialog/confirm-delete-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FilmListComponent implements OnInit, AfterViewInit {

  loading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  initialLoading$: Observable<boolean> | undefined;
  filmDeletedEvt$ = new Subject<Film>();

  filmDataSource$: Observable<MatTableDataSource<Film>> | undefined;
  displayedColumns: string[] = ['title', 'name', 'length', 'rental_rate', 'rating', 'delete'];

  readonly PAGE_SIZE: number = 10;

  //Services
  filmsService = inject(FilmsService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  dialog = inject(MatDialog);
  snackBar = inject(MatSnackBar);

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  ngOnInit(): void {
    this.checkForDataChanges();
    this.setupInitialLoading();
  }

  ngAfterViewInit(): void {
    this.emitInitialPageEvent();
  }

  setupInitialLoading(): void {
    this.initialLoading$ = this.loading$?.pipe(
      take(3),
      map(loading => loading)
    );
  }
  
  emitInitialPageEvent(): void {
    this.paginator?.page.emit({ pageIndex: 0, pageSize: 0, length: 0 });
  }

  checkForDataChanges(): void {
    const filmDeleted$ = this.filmDeletedEvt$.pipe(
      switchMap(film => this.filmsService.deleteFilm(film.film_id.toString())),
      tap(_ => this.snackBar.open("Film deleted!", "Ok", { duration: 2000 }))
    );

    this.filmDataSource$ = this.paginator?.page.pipe(
      mergeWith(filmDeleted$),
      tap(_ => this.loading$.next(true)),
      switchMap(_ => {
        const pageIndex = this.paginator?.pageIndex ? this.paginator?.pageIndex : 0;
        return this.filmsService.getFilms(this.PAGE_SIZE, pageIndex);
      }),
      delay(500), //simulating network delay
      tap(_ => this.loading$.next(false)),
      map(filmsData => this.setupFilmsDataSource(filmsData))
    );
  }

  setupFilmsDataSource(filmsData: FilmsData): MatTableDataSource<Film> {
    let filmsDataSource = new MatTableDataSource(filmsData.data);
    if (this.paginator) { this.paginator.length = filmsData.total_count; }
    return filmsDataSource;
  }

  goToDetails(film: Film): void {
    this.router.navigate(['film/' + film.film_id], { relativeTo: this.route });
  }

  goToNew(): void {
    this.router.navigate(['film'], { relativeTo: this.route });
  }

  delete(film: Film, event: any): void {
    event.stopPropagation();
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, { data: film});
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) { this.filmDeletedEvt$.next(film); }
    });
  }

}
