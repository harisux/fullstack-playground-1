import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'domain-select', pathMatch: 'full' },
  { path: 'domain-select', loadChildren: () => import('./domain-select/domain-select.module').then(m => m.DomainSelectModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
