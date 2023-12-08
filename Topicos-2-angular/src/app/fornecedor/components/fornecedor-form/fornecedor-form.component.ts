import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Fornecedor } from 'src/app/models/fornecedor.model';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-fornecedor-form',
  templateUrl: './fornecedor-form.component.html',
  styleUrls: ['./fornecedor-form.component.css']
})
export class FornecedorFormComponent {

  formGroup: FormGroup;
  apiResponse: any = null;

  constructor(private formBuilder: FormBuilder,
              private fornecedorService: FornecedorService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const fornecedor: Fornecedor = this.activatedRoute.snapshot.data['fornecedor'];

    this.formGroup = formBuilder.group({
      id:[(fornecedor && fornecedor.id) ? fornecedor.id : null],
      nome:[(fornecedor && fornecedor.nome) ? fornecedor.nome : '', Validators.required],
      email:[(fornecedor && fornecedor.email) ? fornecedor.email : '', Validators.required]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const fornecedor = this.formGroup.value;
      if (fornecedor.id == null) {
        this.fornecedorService.save(fornecedor).subscribe({
          next: (fornecedorCadastrado) => {
            this.router.navigateByUrl('/admin/fornecedores/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('email')?.setErrors({ apiError: this.getErrorMessage('email') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
         }
        });
      } else {
        this.fornecedorService.update(fornecedor).subscribe({
          next: (fornecedorCadastrado) => {
            this.router.navigateByUrl('/admin/fornecedores/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('email')?.setErrors({ apiError: this.getErrorMessage('email') });
     
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
    const fornecedor = this.formGroup.value;
    if (fornecedor.id != null) {
      this.fornecedorService.delete(fornecedor).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/admin/fornecedores/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
