import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Compra } from 'src/app/models/compra.model';
import { BandeiraCartao } from 'src/app/models/pagamento.model';
import { Usuario } from 'src/app/models/usuario.model';
import { AuthService } from 'src/app/services/auth.service';
import { CompraService } from 'src/app/services/compra.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-finalizar-compra',
  templateUrl: './finalizar-compra.component.html',
  styleUrls: ['./finalizar-compra.component.css']
})
export class FinalizarCompraComponent implements OnInit {
  formGroup: FormGroup;
  apiResponse: any = null;

  compra!: Compra;
  usuario!: Usuario;
  bandeiraCartoes!: BandeiraCartao[];

  constructor(private formBuilder: FormBuilder, private compraService: CompraService,private authService: AuthService ,
    private usuarioService: UsuarioService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
      //const usuario: Usuario = this.activatedRoute.snapshot.data['usuario'];

      this.formGroup = formBuilder.group({
        numeroCartao:['', Validators.required],
        nomeImpressoCartao:['', Validators.required],
        cpfTitular:['', Validators.required],
        bandeiraCartao:[null]
      })

  }


  ngOnInit(): void {

    this.compraService.getCompraEmAndamento() // Você pode especificar o número de página e tamanho da página
    .subscribe((compra: Compra) => {
      this.compra = compra;
      this.initializeForm();
    });

    this.compraService.findBandeiras().subscribe((data) => {
      this.bandeiraCartoes = data;
      console.log(this.bandeiraCartoes);
      this.initializeForm();
    });

    
    this.authService.getUsuarioLogado().subscribe((usuario: Usuario | null) => {
      if(usuario){
      this.usuario = usuario;
      }
      this.initializeForm();
    });

    this.initializeForm();
    
  }

  initializeForm(){

    this.compraService.getCompraEmAndamento() // Você pode especificar o número de página e tamanho da página
    .subscribe((compra: Compra) => {
      this.compra = compra;
    });

    console.log(this.compra);

    // this.formGroup = this.formBuilder.group({
    //   id: [this.compra?.id ?? null], 
    //   dataCompra: [this.compra?.dataCompra ?? '', Validators.required], 
    //   totalCompra: [this.compra?.totalCompra ?? '', Validators.required],
    //   ifConcluida: [this.compra?.ifConcluida ?? false, Validators.required], 
    
    //   endereco: this.formBuilder.group({
    //     logradouro: [this.compra?.endereco?.logradouro ?? '', Validators.required], 
    //     bairro: [this.compra?.endereco?.bairro ?? '', Validators.required],
    //     numero: [this.compra?.endereco?.numero ?? '', Validators.required],
    //     complemento: [this.compra?.endereco?.complemento ?? ''], 
    //     cep: [this.compra?.endereco?.cep ?? '', Validators.required]
    //   }),
    //   pagamento: this.compra?.pagamento ?? this.formBuilder.group({ 
    //     id: [this.compra?.pagamento?.id ?? null]
    //    }), 
    //   usuario: this.compra?.usuario ?? this.formBuilder.group({ 
    //     login: [(this.compra?.usuario && this.compra.usuario.login) || '', Validators.required]
    //    }), 
    //   itemCompra: this.formBuilder.array(
    //     this.compra?.itemCompra?.map(item => this.formBuilder.group({})) ?? []
    //   ) 
    // });
  }

  realizarPagamentoPix() {
      this.compraService.pagarPix().subscribe(
        (response) => {
          console.log('Item adicionado com sucesso!', response);
          this.router.navigateByUrl('user/compra/finalizar-compra');
        },
        (error) => {
          console.error('Erro ao adicionar item ao carrinho', error);
          // Trate o erro conforme necessário
        }
      );
  }

  realizarPagamentoCartao() {
    this.compraService.pagarCartao(this.formGroup.value).subscribe(
      (response) => {
        console.log('Item adicionado com sucesso!', response);
        this.router.navigateByUrl('user/compra/finalizar-compra');
      },
      (error) => {
        console.error('Erro ao adicionar item ao carrinho', error);
        // Trate o erro conforme necessário
      }
    );
}

  realizarPagamentoBoleto() {
    this.compraService.pagarPix().subscribe(
      (response) => {
        console.log('Item adicionado com sucesso!', response);
        this.router.navigateByUrl('user/compra/finalizar-compra');
      },
      (error) => {
        console.error('Erro ao adicionar item ao carrinho', error);
        // Trate o erro conforme necessário
      }
    );
}

step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

}
