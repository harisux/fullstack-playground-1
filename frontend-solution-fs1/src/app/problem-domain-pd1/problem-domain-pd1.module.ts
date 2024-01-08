import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProblemDomainPd1RoutingModule } from './problem-domain-pd1-routing.module';
import { FilmListComponent } from './components/film-list/film-list.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { HttpClientModule } from '@angular/common/http';
import { BackendSelectComponent } from '../backend-select/backend-select.component';
import { FilmDetailComponent } from './components/film-detail/film-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input'; 
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { ConfirmDeleteDialogComponent } from './components/film-list/confirm-delete-dialog/confirm-delete-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    FilmListComponent,
    FilmDetailComponent,
    ConfirmDeleteDialogComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    BackendSelectComponent,
    MatProgressBarModule,
    MatTableModule,
    MatPaginatorModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatSnackBarModule,
    MatIconModule,
    MatDialogModule,
    ProblemDomainPd1RoutingModule
  ]
})
export class ProblemDomainPd1Module { }
