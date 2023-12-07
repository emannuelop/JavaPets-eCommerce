import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProdutoFormComponent } from './components/produto-form/produto-form.component';
import { ProdutoListComponent } from './components/produto-list/produto-list.component';
import { produtoResolver } from './resolver/produto.resolver';
import { ProdutoCardListComponent } from './components/produto-card-list/produto-card-list.component';
import { CarrinhoComponent } from './components/carrinho/carrinho.component';

const routes: Routes = [
  {path: 'list', component: ProdutoListComponent},
  {path: 'carrinho', component: CarrinhoComponent},
  {path: 'new', component: ProdutoFormComponent},
  {path: 'edit/:id', component: ProdutoFormComponent, resolve: {produto: produtoResolver}},
  {path: 'card-list', component: ProdutoCardListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdutoRoutingModule { }
