import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CidadeListComponent } from './components/cidade-list/cidade-list.component';
import { CidadeFormComponent } from './components/cidade-form/cidade-form.component';
import { cidadeResolver } from './resolver/cidade-resolver';

const routes: Routes = [
  {path: 'list', component: CidadeListComponent},
  {path: 'new', component: CidadeFormComponent},
  {path: 'edit/:id', component: CidadeFormComponent, resolve: {cidade: cidadeResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CidadeRoutingModule { }
