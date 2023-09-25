import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, switchMap, tap } from 'rxjs';
import { Film } from '../../models/films';
import { FilmsService } from '../../services/films.service';

@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.scss']
})
export class FilmDetailComponent implements OnInit {

  selectedFilm$: Observable<Film> | undefined;
  languages$: Observable<any> | undefined;
  
  //Services
  filmsService = inject(FilmsService);
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);

  //Form
  filmForm = this.fb.group({
    title: ['', Validators.required],
    description: '',
    release_year: [0, Validators.required],
    language: this.fb.group({
      language_id: 0,
      name: ''
    }),
    original_language: this.fb.group({
      language_id: 0,
      name: ''
    }),
    rental_duration: 0,
    rental_rate: 0,
    length: 0,
    replacement_cost: 0,
    rating: ['', Validators.required],
    special_features: '',
  });

  ngOnInit(): void {
    this.selectedFilm$ = this.route.paramMap.pipe(
        map(params => params.get('id')),
        switchMap(id => this.filmsService.getFilm(id || '0')),
        tap(film => this.initializeForm(film))
    );
  }

  initializeForm(film: Film): void {
    this.filmForm.patchValue({
      title: film.title,
      description: film.description,
      release_year: film.release_year,

    });
  }

  submitFilm(): void {

  }

}
