import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CupomDesconto } from 'src/app/models/cupomDesconto.model';
import { CupomDescontoService } from 'src/app/services/cupomDesconto.service';

@Component({
  selector: 'app-estado-form',
  templateUrl: './cupomDesconto-form.component.html',
  styleUrls: ['./cupomDesconto-form.component.css']
})
export class CupomDescontoFormComponent {
  formGroup: FormGroup;
  apiResponse: any = null;

  constructor(private formBuilder: FormBuilder,
              private cupomDescontoService: CupomDescontoService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const cupomDesconto: CupomDesconto = this.activatedRoute.snapshot.data['cupomDesconto'];

    this.formGroup = formBuilder.group({
      id:[(cupomDesconto && cupomDesconto.id) ? cupomDesconto.id : null],
      codigoCupom:[(cupomDesconto && cupomDesconto.codigoCupom) ? cupomDesconto.codigoCupom : '', Validators.required],
      quantidadeDisponivel:[(cupomDesconto && cupomDesconto.quantidadeDisponivel) ? cupomDesconto.quantidadeDisponivel : null],
      porcentagemDesconto:[(cupomDesconto && cupomDesconto.porcentagemDesconto) ? cupomDesconto.porcentagemDesconto : null]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const cupomDesconto = this.formGroup.value;
      if (cupomDesconto.id == null) {
        this.cupomDescontoService.save(cupomDesconto).subscribe({
          next: (cupomDescontoCadastrado) => {
            this.router.navigateByUrl('/cupomDescontos/list');
          },
          error: (errorResponse) => {
             // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('codigoCupom')?.setErrors({ apiError: this.getErrorMessage('codigoCupom') });
           this.formGroup.get('quantidadeDisponivel')?.setErrors({ apiError: this.getErrorMessage('quantidadeDisponivel') });
           this.formGroup.get('porcentagemDesconto')?.setErrors({ apiError: this.getErrorMessage('porcentagemDesconto') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
          }
        });
      } else {
        this.cupomDescontoService.update(cupomDesconto).subscribe({
          next: (cupomDescontoCadastrado) => {
            this.router.navigateByUrl('/cupomDescontos/list');
          },
          error: (errorResponse) => {
            // Processar erros da API
          this.apiResponse = errorResponse.error; 

          // Associar erros aos campos do formulário
          this.formGroup.get('codigoCupom')?.setErrors({ apiError: this.getErrorMessage('codigoCupom') });
          this.formGroup.get('quantidadeDisponivel')?.setErrors({ apiError: this.getErrorMessage('quantidadeDisponivel') });
          this.formGroup.get('porcentagemDesconto')?.setErrors({ apiError: this.getErrorMessage('porcentagemDesconto') });
    
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
    const cupomDesconto = this.formGroup.value;
    if (cupomDesconto.id != null) {
      this.cupomDescontoService.delete(cupomDesconto).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/cupomDescontos/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
