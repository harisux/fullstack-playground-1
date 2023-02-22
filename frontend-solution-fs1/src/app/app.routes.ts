import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
    { path: '', redirectTo: 'domain', pathMatch: 'full' },
    { path: 'domain', loadComponent: () => import('./domain-select/domain-select.component').then(m => m.DomainSelectComponent) },
    { path: 'domain/:id', loadComponent: () => import('./backend-select/backend-select.component').then(m => m.BackendSelectComponent) },
    { path: 'domain/pd1/show', loadChildren: () => import('./problem-domain-pd1/problem-domain-pd1.module').then(m => m.ProblemDomainPd1Module) },
];