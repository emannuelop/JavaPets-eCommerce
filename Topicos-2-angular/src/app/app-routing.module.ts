import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'estados', loadChildren:
      () => import('./estado/estado.module')
        .then(m => m.EstadoModule)
  },

  {
    path: 'cidades', loadChildren:
      () => import('./cidade/cidade.module')
        .then(m => m.CidadeModule)
  },

  {
    path: 'cupomDescontos', loadChildren:
      () => import('./cupomDesconto/cupomDesconto.module')
        .then(m => m.CupomDescontoModule)
  },

  {
    path: 'marcas', loadChildren:
      () => import('./marca/marca.module')
        .then(m => m.MarcaModule)
  },

  {
    path: 'fornecedores', loadChildren:
      () => import('./fornecedor/fornecedor.module')
        .then(m => m.FornecedorModule)
  },

  {
    path: 'categorias', loadChildren:
      () => import('./categoria/categoria.module')
        .then(m => m.CategoriaModule)
  },

  {
    path: 'produtos', loadChildren:
      () => import('./produto/produto.module')
        .then(m => m.ProdutoModule)
  },

  {
    path:'usuarios', loadChildren:
    () => import('./usuario/usuario.module')
    .then(m => m.UsuarioModule)
  },

  {
    path:'pets', loadChildren:
    () => import('./pet/pet.module')
    .then(m => m.PetModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
