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

  constructor(private formBuilder: FormBuilder,
              private categoriaService: CategoriaService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const categoria: Categoria = this.activatedRoute.snapshot.data['categoria'];

    this.formGroup = formBuilder.group({
      id:[(categoria && categoria.id) ? categoria.id : null],
      nome:[(categoria && categoria.nome) ? categoria.nome : '', Validators.required],
      descricao:[(categoria && categoria.descricao) ? categoria.descricao : '', Validators.required]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const categoria = this.formGroup.value;
      if (categoria.id == null) {
        this.categoriaService.save(categoria).subscribe({
          next: (categoriaCadastrado) => {
            this.router.navigateByUrl('/categorias/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.categoriaService.update(categoria).subscribe({
          next: (categoriaCadastrado) => {
            this.router.navigateByUrl('/categorias/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
  }

  excluir() {
    const categoria = this.formGroup.value;
    if (categoria.id != null) {
      this.categoriaService.delete(categoria).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/categorias/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }
}
