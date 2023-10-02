import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriaFormComponent } from './components/categoria-form/categoria-form.component';
import { CategoriaListComponent } from './components/categoria-list/categoria-list.component';
import { categoriaResolver } from './resolver/categoria.resolver';

const routes: Routes = [
  {path: 'list', component: CategoriaListComponent},
  {path: 'new', component: CategoriaFormComponent},
  {path: 'edit/:id', component: CategoriaFormComponent, resolve: {categoria: categoriaResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriaRoutingModule { }
