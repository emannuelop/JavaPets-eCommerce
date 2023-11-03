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
  apiResponse: any = null;

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
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('especie')?.setErrors({ apiError: this.getErrorMessage('especie') });
           this.formGroup.get('raca')?.setErrors({ apiError: this.getErrorMessage('raca') });
           this.formGroup.get('idadeEmMeses')?.setErrors({ apiError: this.getErrorMessage('idadeEmMeses') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
         }
        });
      } else {
        this.petService.update(pet).subscribe({
          next: (petCadastrado) => {
            this.router.navigateByUrl('/pets/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('especie')?.setErrors({ apiError: this.getErrorMessage('especie') });
           this.formGroup.get('raca')?.setErrors({ apiError: this.getErrorMessage('raca') });
           this.formGroup.get('idadeEmMeses')?.setErrors({ apiError: this.getErrorMessage('idadeEmMeses') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
         }
        });        
      }
    }
  }

  getErrorMessage(fieldName: string): string {
    const error = this.apiResponse.errors.find((error: any) => error.fieldName === fieldName);
    return error ? error.message : '';
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
