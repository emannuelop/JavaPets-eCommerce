import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Pet } from 'src/app/models/pet.model';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-form',
  templateUrl: './pet-form.component.html',
  styleUrls: ['./pet-form.component.css']
})
export class PetFormComponent {
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private petService: PetService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const pet: Pet = this.activatedRoute.snapshot.data['pet'];

    this.formGroup = formBuilder.group({
      id:[(pet && pet.id) ? pet.id : null],
      nome:[(pet && pet.nome) ? pet.nome : '', Validators.required],
      especie:[(pet && pet.especie) ? pet.especie : '', Validators.required],
      raca:[(pet && pet.raca) ? pet.raca : '', Validators.required],
      idadeEmMeses:[(pet && pet.idadeEmMeses) ? pet.idadeEmMeses : null]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const pet = this.formGroup.value;
      if (pet.id == null) {
        this.petService.save(pet).subscribe({
          next: (petCadastrado) => {
            this.router.navigateByUrl('/pets/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.petService.update(pet).subscribe({
          next: (petCadastrado) => {
            this.router.navigateByUrl('/pets/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
  }

  excluir() {
    const pet = this.formGroup.value;
    if (pet.id != null) {
      this.petService.delete(pet).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/pets/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
