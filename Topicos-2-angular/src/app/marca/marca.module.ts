import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MarcaRoutingModule } from './marca-routing.module';
import { MarcaListComponent } from './components/marca-list/marca-list.component';
import { MarcaFormComponent } from './components/marca-form/marca-form.component';

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

@NgModule({
  declarations: [
    MarcaListComponent,
    MarcaFormComponent
  ],
  imports: [
    CommonModule,
    MarcaRoutingModule,
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
    FormsModule
  ]
})
export class MarcaModule { }
