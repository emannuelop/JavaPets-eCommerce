import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FornecedorListComponent } from './components/fornecedor-list/fornecedor-list.component';
import { FornecedorFormComponent } from './components/fornecedor-form/fornecedor-form.component';
import { fornecedorResolver } from './resolver/fornecedor.resolver';

const routes: Routes = [
  {path: 'list', component: FornecedorListComponent},
  {path: 'new', component: FornecedorFormComponent},
  {path: 'edit/:id', component: FornecedorFormComponent, resolve: {fornecedor: fornecedorResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FornecedorRoutingModule { }
