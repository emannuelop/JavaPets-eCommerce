import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CupomDescontoRoutingModule } from './cupom-desconto-routing.module';
import { CupomDescontoListComponent } from './components/cupom-desconto-list/cupom-desconto-list.component';
import { CupomDescontoFormComponent } from './components/cupom-desconto-form/cupom-desconto-form.component';


@NgModule({
  declarations: [
    CupomDescontoListComponent,
    CupomDescontoFormComponent
  ],
  imports: [
    CommonModule,
    CupomDescontoRoutingModule
  ]
})
export class CupomDescontoModule { }
