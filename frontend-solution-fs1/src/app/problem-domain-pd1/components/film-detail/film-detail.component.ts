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
  
  //Services
  filmsService = inject(FilmsService);
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);

  //Form
  filmForm = this.fb.group({
    title: ['', Validators.required],
    description: '',
  });

  ngOnInit(): void {
    this.selectedFilm$ = this.route.paramMap.pipe(
        map(params => params.get('id')),
        switchMap(id => this.filmsService.getFilm(id || '0')),
        tap(film => this.initializeForm(film))
    );
  }

  initializeForm(film: Film): void {
    this.filmForm.controls.title.setValue(film.title);
    this.filmForm.controls.description.setValue(film.description); 
  }

  submitFilm(): void {

  }

}
