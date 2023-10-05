import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PetListComponent } from './components/pet-list/pet-list.component';
import { PetFormComponent } from './components/pet-form/pet-form.component';
import { petResolver } from './resolver/pet-resolver';

const routes: Routes = [
  {path: 'list', component: PetListComponent},
  {path: 'new', component: PetFormComponent},
  {path: 'edit/:id', component: PetFormComponent, resolve: {pet: petResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PetRoutingModule { }
