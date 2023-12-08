import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Cidade } from 'src/app/models/cidade.model';
import { Usuario } from 'src/app/models/usuario.model';
import { CidadeService } from 'src/app/services/cidade.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent {
  formGroup: FormGroup;
cidades:Cidade[] =[];
apiResponse: any = null;

cidadeControl: FormControl = new FormControl(null);
  constructor(private formBuilder: FormBuilder,
    private usuarioService: UsuarioService,
    private cidadeService: CidadeService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
      //const usuario: Usuario = this.activatedRoute.snapshot.data['usuario'];

    this.formGroup = this.formBuilder.group({
      id: [null],
      login: ['', Validators.required],
      senha: ['', Validators.required],

      pessoaFisicaDto: this.formBuilder.group({
        nome: ['', Validators.required],
        cpf: ['', Validators.required],
        email: ['', Validators.required],
        sexo: ['', Validators.required],
      }),

      endereco: this.formBuilder.group({
        logradouro: ['', Validators.required],
        bairro: ['', Validators.required],
        numero: ['', Validators.required],
        complemento: '',
        cep: ['', Validators.required],
        cidade: [null]
      }),
       telefones: this.formBuilder.array([]) 
    });
  }



  ngOnInit(): void {
    this.initializeForm();

    this.cidadeService.findAll(0, 100) // Você pode especificar o número de página e tamanho da página
    .subscribe((cidades: Cidade[]) => {
      this.cidades = cidades;
      this.initializeForm();
    });
    this.cidadeControl.valueChanges.subscribe((cidadeSelecionada) => {
      
      if (this.formGroup && this.formGroup.get('endereco')) {
        this.formGroup.get('endereco')?.get('idMunicipio')?.setValue(cidadeSelecionada?.id);
        this.initializeForm();
      }
    });

this.initializeForm();
this.initializeForm();
    
  }

 
  initializeForm() {

    const usuario: Usuario = this.activatedRoute.snapshot.data['usuario'];
    const sexo = usuario?.pessoaFisicaDto?.sexo?.toString() || null;
    const isEdicao = !!usuario;
    this.formGroup = this.formBuilder.group({
      id: [(usuario && usuario.id) || null],
      login: [(usuario && usuario.login) || '', Validators.required],
      senha: [(usuario && usuario.senha) || '', Validators.required],
      
      pessoaFisicaDto: this.formBuilder.group({
        nome: [(usuario && usuario.pessoaFisicaDto && usuario.pessoaFisicaDto.nome) || '', Validators.required],
        cpf: [ (usuario && usuario.pessoaFisicaDto && usuario.pessoaFisicaDto.cpf) || '', Validators.required],
        email: [(usuario && usuario.pessoaFisicaDto && usuario.pessoaFisicaDto.email) || '', Validators.required],
        sexo: [sexo, Validators.required]
      }),
      
      endereco: this.formBuilder.group({
        logradouro: [(usuario && usuario.endereco.logradouro) || '', Validators.required],
        bairro: [(usuario && usuario.endereco.bairro) || '', Validators.required],
        numero: [(usuario && usuario.endereco.numero) || '', Validators.required],
        complemento: [(usuario && usuario.endereco.complemento) || ''],
        cep: [(usuario && usuario.endereco.cep) || '', Validators.required],
        cidade: [(usuario && usuario.endereco.cidade) || null, Validators.required]
      }),
      telefones: this.formBuilder.array([]) // Inicialize como uma lista vazia
    });

    // Preenche os telefones do FormArray com os telefones do usuário, se houver telefones
     if (usuario && usuario.telefones && usuario.telefones.length > 0) {
       const telefonesFormArray = this.formGroup.get('telefones') as FormArray;
       usuario.telefones.forEach(telefone => {
         const telefoneFormGroup = this.formBuilder.group({
           codigoDeArea: [telefone.codigoDeArea || ''],
           numero: [telefone.numero || '']
         });
         telefonesFormArray.push(telefoneFormGroup);
       });
     }

    console.log(this.formGroup.value);
    console.log(usuario);

  }

  salvar() {
    if (this.formGroup.valid) {
      const usuario = this.formGroup.value;
  
      // Antes de enviar a requisição, adicione o telefone ao objeto do usuário
      usuario.telefones = this.telefonesFormArray.value;
  console.log(usuario.telefones);
      if (usuario.id == null) {
        this.usuarioService.save(usuario).subscribe({
          next: (usuarioCadastrado) => {
            console.log(this.router.url)
            if(this.router.url == '/user/usuarios/new'){
              this.router.navigateByUrl('/auth/login');
            }else{
              this.router.navigateByUrl('/usuarios/list');
            }
          },
          error: (errorResponse) => {
            this.apiResponse = errorResponse.error;

            this.formGroup.get('login')?.setErrors({ apiError: this.getErrorMessage('login') });
            this.formGroup.get('senha')?.setErrors({apiError: this.getErrorMessage('descricao')});

            this.formGroup.get('pessoaFisicaDto')?.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
            this.formGroup.get('pessoaFisicaDto')?.get('cpf')?.setErrors({ apiError: this.getErrorMessage('cpf') });
            this.formGroup.get('pessoaFisicaDto')?.get('email')?.setErrors({ apiError: this.getErrorMessage('email') });
            this.formGroup.get('pessoaFisicaDto')?.get('sexo')?.setErrors({ apiError: this.getErrorMessage('sexo') });

            this.formGroup.get('endereco')?.get('logradouro')?.setErrors({ apiError: this.getErrorMessage('logradouro') });
            this.formGroup.get('endereco')?.get('bairro')?.setErrors({ apiError: this.getErrorMessage('bairro') });
            this.formGroup.get('endereco')?.get('numero')?.setErrors({ apiError: this.getErrorMessage('numero') });
            this.formGroup.get('endereco')?.get('cep')?.setErrors({ apiError: this.getErrorMessage('cep') });
            this.formGroup.get('endereco')?.get('cidade')?.setErrors({ apiError: this.getErrorMessage('cidade') });


            
            console.log('Erro ao incluir' + JSON.stringify(errorResponse));
          }
        });
      } else {
        this.usuarioService.update(usuario).subscribe({
          next: (UsuarioCadastrado) => {
            this.router.navigateByUrl('/admin/usuarios/list');
          },
          error: (errorResponse) => {
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
    const usuario = this.formGroup.value;
    if (usuario.id != null) {
      this.usuarioService.delete(usuario).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/admin/usuarios/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }
  }

  trocarUrl(){
    if(this.router.url == '/user/usuarios/new'){
      this.router.navigateByUrl('/auth/login')
    }
    else{
      this.router.navigateByUrl('/admin/usuarios/list')
    }
  }

  // No seu componente TypeScript

  get telefonesFormArray() {
    return this.formGroup.get('telefones') as FormArray;
  }

  // Adicionar um novo telefone ao FormArray
  adicionarTelefone() {
    this.telefonesFormArray.push(this.formBuilder.group({
      codigoDeArea: '',
      numero: ''
    }));
  }

  // Remover um telefone do FormArray pelo índice
  removerTelefone(index: number) {
    this.telefonesFormArray.removeAt(index);
  }

}
