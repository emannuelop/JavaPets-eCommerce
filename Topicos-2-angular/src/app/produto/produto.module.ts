import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProdutoRoutingModule } from './produto-routing.module';
import { ProdutoListComponent } from './components/produto-list/produto-list.component';
import { ProdutoFormComponent } from './components/produto-form/produto-form.component';

import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';

import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { MatPaginatorModule} from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl'
import { ProdutoCardListComponent } from './components/produto-card-list/produto-card-list.component';

@NgModule({
  declarations: [
    ProdutoListComponent,
    ProdutoFormComponent,
    ProdutoCardListComponent
  ],
  imports: [
    CommonModule,
    ProdutoRoutingModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatTableModule,
    MatPaginatorModule,
    FormsModule,
    MatDialogModule
  ],
  providers: [
    CustomPaginatorIntl
  ]
})
export class ProdutoModule { }
