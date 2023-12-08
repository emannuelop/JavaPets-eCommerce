import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/models/categoria.model';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-form',
  templateUrl: './categoria-form.component.html',
  styleUrls: ['./categoria-form.component.css']
})
export class CategoriaFormComponent {
  formGroup: FormGroup;
  apiResponse: any = null;

  constructor(private formBuilder: FormBuilder,
    private categoriaService: CategoriaService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {

    const categoria: Categoria = this.activatedRoute.snapshot.data['categoria'];

    this.formGroup = formBuilder.group({
      id: [(categoria && categoria.id) ? categoria.id : null],
      nome: [(categoria && categoria.nome) ? categoria.nome : '', Validators.required],
      descricao: [(categoria && categoria.descricao) ? categoria.descricao : '', Validators.required]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const categoria = this.formGroup.value;
      if (categoria.id == null) {
        this.categoriaService.save(categoria).subscribe({
          next: (categoriaCadastrado) => {
            this.router.navigateByUrl('/admin/categorias/list');
          },
          error: (errorResponse) => {
            this.apiResponse = errorResponse.error;

            this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
            this.formGroup.get('descricao')?.setErrors({apiError: this.getErrorMessage('descricao')});

            console.log('Erro ao incluir' + JSON.stringify(errorResponse));
          }
        });
      } else {
        this.categoriaService.update(categoria).subscribe({
          next: (categoriaCadastrado) => {
            this.router.navigateByUrl('/admin/categorias/list');
          },
          error: (errorResponse) => {
            this.apiResponse = errorResponse.error;

          this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
          this.formGroup.get('descricao')?.setErrors({apiError: this.getErrorMessage('descricao')});
            console.log('Erro ao alterar' + JSON.stringify(errorResponse));
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
    const categoria = this.formGroup.value;
    if (categoria.id != null) {
      this.categoriaService.delete(categoria).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/admin/categorias/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }
  }
}
