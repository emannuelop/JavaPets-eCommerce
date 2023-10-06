import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstadoRoutingModule } from './estado-routing.module';
import { EstadoListComponent } from './components/estado-list/estado-list.component';
import { EstadoFormComponent } from './components/estado-form/estado-form.component';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';

import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule} from '@angular/material/paginator';
import { FormsModule } from '@angular/forms'; 
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    EstadoListComponent,
    EstadoFormComponent
  ],
  imports: [
    CommonModule,
    EstadoRoutingModule,
    MatTableModule,
    MatToolbarModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatPaginatorModule,
    FormsModule,
    MatDialogModule
  ]
})
export class EstadoModule { }
