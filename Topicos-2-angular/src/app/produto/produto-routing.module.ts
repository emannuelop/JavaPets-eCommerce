import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProdutoFormComponent } from './components/produto-form/produto-form.component';
import { ProdutoListComponent } from './components/produto-list/produto-list.component';
import { produtoResolver } from './resolver/produto.resolver';

const routes: Routes = [
  {path: 'list', component: ProdutoListComponent},
  {path: 'new', component: ProdutoFormComponent},
  {path: 'edit/:id', component: ProdutoFormComponent, resolve: {produto: produtoResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdutoRoutingModule { }
