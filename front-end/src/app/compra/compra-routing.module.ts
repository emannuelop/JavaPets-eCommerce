import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FinalizarCompraComponent } from './finalizar-compra/finalizar-compra.component';
import { PedidosComponent } from './pedidos/pedidos.component';

const routes: Routes = [
  {path: 'finalizar-compra', component: FinalizarCompraComponent},
  {path: 'meus-pedidos', component: PedidosComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompraRoutingModule { }
