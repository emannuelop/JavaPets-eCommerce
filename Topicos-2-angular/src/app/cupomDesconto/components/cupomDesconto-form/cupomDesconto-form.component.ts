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
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.cupomDescontoService.update(cupomDesconto).subscribe({
          next: (cupomDescontoCadastrado) => {
            this.router.navigateByUrl('/cupomDescontos/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
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
