import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Marca } from 'src/app/models/marca.model';
import { MarcaService } from 'src/app/services/marca.service';

@Component({
  selector: 'app-marca-form',
  templateUrl: './marca-form.component.html',
  styleUrls: ['./marca-form.component.css']
})
export class MarcaFormComponent {
  formGroup: FormGroup;
  apiResponse: any = null;

  constructor(private formBuilder: FormBuilder,
              private marcaService: MarcaService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const marca: Marca = this.activatedRoute.snapshot.data['marca'];

    this.formGroup = formBuilder.group({
      id:[(marca && marca.id) ? marca.id : null],
      nome:[(marca && marca.nome) ? marca.nome : '', Validators.required],
      cnpj:[(marca && marca.cnpj) ? marca.cnpj : '', Validators.required]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const marca = this.formGroup.value;
      if (marca.id == null) {
        this.marcaService.save(marca).subscribe({
          next: (marcaCadastrado) => {
            this.router.navigateByUrl('/admin/marcas/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('cnpj')?.setErrors({ apiError: this.getErrorMessage('cnpj') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
         }
        });
      } else {
        this.marcaService.update(marca).subscribe({
          next: (marcaCadastrado) => {
            this.router.navigateByUrl('/admin/marcas/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('cnpj')?.setErrors({ apiError: this.getErrorMessage('cnpj') });
     
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
    const marca = this.formGroup.value;
    if (marca.id != null) {
      this.marcaService.delete(marca).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/admin/marcas/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
