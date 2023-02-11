import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DomainSelectComponent } from './domain-select.component';

const routes: Routes = [{ path: '', component: DomainSelectComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DomainSelectRoutingModule { }
