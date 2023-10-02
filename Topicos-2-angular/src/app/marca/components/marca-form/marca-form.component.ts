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
            this.router.navigateByUrl('/marcas/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.marcaService.update(marca).subscribe({
          next: (marcaCadastrado) => {
            this.router.navigateByUrl('/marcas/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
  }

  excluir() {
    const marca = this.formGroup.value;
    if (marca.id != null) {
      this.marcaService.delete(marca).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/marcas/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
