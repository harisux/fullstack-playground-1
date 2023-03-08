import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, switchMap } from 'rxjs';
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

  ngOnInit(): void {
    this.selectedFilm$ = this.route.paramMap.pipe(
        map(params => params.get('id')),
        switchMap(id => this.filmsService.getFilm(id || '0'))
    );
  }

}
