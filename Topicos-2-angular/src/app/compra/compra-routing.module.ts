import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FinalizarCompraComponent } from './finalizar-compra/finalizar-compra.component';

const routes: Routes = [
  {path: 'finalizar-compra', component: FinalizarCompraComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompraRoutingModule { }
