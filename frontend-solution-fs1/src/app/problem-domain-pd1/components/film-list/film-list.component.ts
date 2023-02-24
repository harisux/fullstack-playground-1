import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FilmsService } from '../../services/films.service';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FilmListComponent implements OnInit {

  dataSource$: Observable<any> | undefined;
  displayedColumns: string[] = ['title', 'language', 'length', 'rentalRate', 'rating'];

  //Services
  filmsService = inject(FilmsService);

  ngOnInit(): void {
    this.dataSource$ = this.filmsService.getFilms();
  }

}
