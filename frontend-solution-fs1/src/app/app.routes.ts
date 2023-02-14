import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
    { path: '', redirectTo: 'domain-select', pathMatch: 'full' },
    { path: 'domain-select', loadComponent: () => import('./domain-select/domain-select.component').then(m => m.DomainSelectComponent) }
];