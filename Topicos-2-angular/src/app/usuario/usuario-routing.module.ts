import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioListComponent } from './components/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './components/usuario-form/usuario-form.component';
import { usuarioResolver } from './resolver/usuario.resolver';
import { UsuarioDetalhesComponent } from './components/usuario-detalhes/usuario-detalhes.component';

const routes: Routes = [
  { path: 'list', component: UsuarioListComponent },
  { path: 'new', component: UsuarioFormComponent },
  { path: 'edit/:id', component: UsuarioFormComponent, resolve: { usuario: usuarioResolver } },
  {path:'detalhes', component: UsuarioDetalhesComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
