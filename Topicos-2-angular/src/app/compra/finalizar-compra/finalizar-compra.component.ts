import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Compra } from 'src/app/models/compra.model';
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

  constructor(private formBuilder: FormBuilder, private compraService: CompraService,private authService: AuthService ,
    private usuarioService: UsuarioService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
      //const usuario: Usuario = this.activatedRoute.snapshot.data['usuario'];

    this.formGroup = this.formBuilder.group({
      id: [null],
      dataCompra: ['', Validators.required],
      totalCompra: [null],
      ifConcluida: ['', Validators.required],

      endereco: this.formBuilder.group({
        logradouro: ['', Validators.required],
        bairro: ['', Validators.required],
        numero: ['', Validators.required],
        complemento: '',
        cep: ['', Validators.required]
      }),
       pagamento: this.formBuilder,
       usuario: this.formBuilder,
       itemCompra: this.formBuilder.array([])
    });
  }


  ngOnInit(): void {

    this.compraService.getCompraEmAndamento() // Você pode especificar o número de página e tamanho da página
    .subscribe((compra: Compra) => {
      this.compra = compra;
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

    this.formGroup = this.formBuilder.group({
      id: [this.compra?.id ?? null], 
      dataCompra: [this.compra?.dataCompra ?? '', Validators.required], 
      totalCompra: [this.compra?.totalCompra ?? '', Validators.required],
      ifConcluida: [this.compra?.ifConcluida ?? false, Validators.required], 
    
      endereco: this.formBuilder.group({
        logradouro: [this.compra?.endereco?.logradouro ?? '', Validators.required], 
        bairro: [this.compra?.endereco?.bairro ?? '', Validators.required],
        numero: [this.compra?.endereco?.numero ?? '', Validators.required],
        complemento: [this.compra?.endereco?.complemento ?? ''], 
        cep: [this.compra?.endereco?.cep ?? '', Validators.required]
      }),
      pagamento: this.compra?.pagamento ?? this.formBuilder.group({ 
        id: [this.compra?.pagamento?.id ?? null]
       }), 
      usuario: this.compra?.usuario ?? this.formBuilder.group({ 
        login: [(this.compra?.usuario && this.compra.usuario.login) || '', Validators.required]
       }), 
      itemCompra: this.formBuilder.array(
        this.compra?.itemCompra?.map(item => this.formBuilder.group({})) ?? []
      ) 
    });
  }
}