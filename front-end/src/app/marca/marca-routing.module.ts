import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MarcaListComponent } from './components/marca-list/marca-list.component';
import { MarcaFormComponent } from './components/marca-form/marca-form.component';
import { marcaResolver } from './resolver/marca.resolver';

const routes: Routes = [
  {path: 'list', component: MarcaListComponent},
  {path: 'new', component: MarcaFormComponent},
  {path: 'edit/:id', component: MarcaFormComponent, resolve: {marca: marcaResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MarcaRoutingModule { }
