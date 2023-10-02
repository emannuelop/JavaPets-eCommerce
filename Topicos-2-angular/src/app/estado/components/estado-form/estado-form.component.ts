import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Estado } from 'src/app/models/estado.model';
import { EstadoService } from 'src/app/services/estado.service';

@Component({
  selector: 'app-estado-form',
  templateUrl: './estado-form.component.html',
  styleUrls: ['./estado-form.component.css']
})
export class EstadoFormComponent {
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private estadoService: EstadoService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

    const estado: Estado = this.activatedRoute.snapshot.data['estado'];

    this.formGroup = formBuilder.group({
      id:[(estado && estado.id) ? estado.id : null],
      nome:[(estado && estado.nome) ? estado.nome : '', Validators.required],
      sigla:[(estado && estado.sigla) ? estado.sigla : '', Validators.required]
    })
  }

  salvar() {
    if (this.formGroup.valid) {
      const estado = this.formGroup.value;
      if (estado.id == null) {
        this.estadoService.save(estado).subscribe({
          next: (estadoCadastrado) => {
            this.router.navigateByUrl('/estados/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.estadoService.update(estado).subscribe({
          next: (estadoCadastrado) => {
            this.router.navigateByUrl('/estados/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
  }

  excluir() {
    const estado = this.formGroup.value;
    if (estado.id != null) {
      this.estadoService.delete(estado).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/estados/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

}
