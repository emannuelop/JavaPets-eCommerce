import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstadoListComponent } from './components/estado-list/estado-list.component';
import { EstadoFormComponent } from './components/estado-form/estado-form.component';
import { estadoResolver } from './resolver/estado-resolver';

const routes: Routes = [
  {path: 'list', component: EstadoListComponent},
  {path: 'new', component: EstadoFormComponent},
  {path: 'edit/:id', component: EstadoFormComponent, resolve: {estado: estadoResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstadoRoutingModule { }
