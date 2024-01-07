import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, combineLatest, map, Observable, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { Film, Language } from '../../models/films';
import { FilmsService } from '../../services/films.service';

interface FormData {
  film: Film;
  languages: Language[];
}

const FILM_RATINGS = [
  'G', 'PG', 'PG-13', 'R', 'NC-17'
];

const MAX_DOLLAR_COST = 999999;

@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.scss']
})
export class FilmDetailComponent implements OnInit {

  loading$ = new BehaviorSubject<boolean>(true);
  destroyed$ = new Subject<void>();

  formData$: Observable<FormData> | undefined;
  filmRatings: string[] = FILM_RATINGS;
  
  //Services
  filmsService = inject(FilmsService);
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);
  router = inject(Router);

  //Form
  filmForm = this.fb.group({
    title: ['', Validators.required],
    description: '',
    release_year: [0, [Validators.required, Validators.pattern(/^(19|20)\d{2}$/)]],
    language_id: [0, Validators.required],
    rental_duration: [0, Validators.pattern(/^\d{1,4}$/)],
    rental_rate: [0, [Validators.min(0), Validators.max(MAX_DOLLAR_COST)]],
    length: [0, Validators.pattern(/^\d{1,3}$/)],
    replacement_cost: [0, [Validators.min(0), Validators.max(MAX_DOLLAR_COST)]],
    rating: ['', Validators.required],
    special_features: '',
  });

  ngOnInit(): void {
    const languages$ = this.filmsService.getLanguages();
    const film$ = this.route.paramMap.pipe(
      map(params => params.get('id')),
      switchMap(id => this.filmsService.getFilm(id || '0'))
    );
    this.formData$ = combineLatest([languages$, film$]).pipe(
        tap(_ => this.loading$.next(true)),
        map(([languages, film]) => { return { languages, film }; }),
        tap(formData => this.initializeForm(formData.film)),
        tap(_ => this.loading$.next(false)),
    );
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  initializeForm(film: Film): void {
    this.filmForm.patchValue({
      title: film.title,
      description: film.description,
      release_year: film.release_year,
      language_id: film.language.language_id,
      rental_duration: film.rental_duration,
      rental_rate: film.rental_rate,
      length: film.length,
      replacement_cost: film.replacement_cost,
      rating: film.rating
    });
  }

  submitFilm(): void {
    const filmId = Number(this.route.snapshot.paramMap.get('id'));
    const filmToUpdate = this.assembleFilmToUpdate(filmId);
    
    this.loading$.next(true);
    this.filmsService.updateFilm(filmToUpdate)
      .pipe(takeUntil(this.destroyed$)).subscribe(_ => {
        this.loading$.next(false);
        this.router.navigate(['../../'], { relativeTo: this.route });
      });
  }

  assembleFilmToUpdate(filmId: number): Film {
    const formValue = this.filmForm.value;
    let film: Film = {
      film_id: filmId,
      title: formValue.title || '',
      description: formValue.description || '',
      release_year: formValue.release_year || 1900,
      language: { 
        language_id: formValue.language_id || 0,
        name: ''
      },
      original_language: null,
      rental_duration: formValue.rental_duration || 0,
      rental_rate: formValue.rental_rate || 0,
      length: formValue.length || 0,
      replacement_cost: formValue.replacement_cost || 0,
      rating: formValue.rating || '',
      special_features: formValue.special_features || ''
    };
    return film;
  }

}
