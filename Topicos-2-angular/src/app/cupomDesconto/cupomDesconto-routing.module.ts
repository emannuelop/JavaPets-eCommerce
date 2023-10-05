import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CupomDescontoListComponent } from './components/cupomDesconto-list/cupomDesconto-list.component';
import { CupomDescontoFormComponent } from './components/cupomDesconto-form/cupomDesconto-form.component';
import { cupomDescontoResolver } from './resolver/cupomDesconto-resolver';

const routes: Routes = [
  {path: 'list', component: CupomDescontoListComponent},
  {path: 'new', component: CupomDescontoFormComponent},
  {path: 'edit/:id', component: CupomDescontoFormComponent, resolve: {cupomDesconto: cupomDescontoResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CupomDescontoRoutingModule { }
