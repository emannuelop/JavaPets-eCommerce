import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from 'src/app/models/usuario.model';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent {
  formGroup: FormGroup;


  constructor(private formBuilder: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {

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
        idMunicipio: ['', Validators.required]
      }),
       telefones: this.formBuilder.array([]) // Inicialize como uma lista vazia
    });
  }



  ngOnInit(): void {


  }

 
  initializeForm() {
    const usuario: Usuario = this.activatedRoute.snapshot.data['usuario'];
    const sexo = usuario?.pessoaFisicaDto?.sexo?.toString() || null;

    this.formGroup = this.formBuilder.group({
      id: [(usuario && usuario.id) || null],
      login: [(usuario && usuario.login) || '', Validators.required],
      senha: [(usuario && usuario.senha) || '', Validators.required],
      pessoaFisicaDto: this.formBuilder.group({
        nome: [(usuario && usuario.pessoaFisicaDto.nome) || '', Validators.required],
        cpf: [(usuario && usuario.pessoaFisicaDto.cpf) || '', Validators.required],
        email: [(usuario && usuario.pessoaFisicaDto.email) || '', Validators.required],
        sexo: [sexo, Validators.required],
      }),
      
      endereco: this.formBuilder.group({
        logradouro: [(usuario && usuario.endereco.logradouro) || '', Validators.required],
        bairro: [(usuario && usuario.endereco.bairro) || '', Validators.required],
        numero: [(usuario && usuario.endereco.numero) || '', Validators.required],
        complemento: [(usuario && usuario.endereco.complemento) || ''],
        cep: [(usuario && usuario.endereco.cep) || '', Validators.required],
        idMunicipio: [(usuario && usuario.endereco.idMunicipio) || null, Validators.required]
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
  }

  // salvar() {
  //   if (this.formGroup.valid) {
  //     const usuario = this.formGroup.value;
  //     if (usuario.id == null) {
  //       this.usuarioService.save(usuario).subscribe({
  //         next: (usuarioCadastrado) => {
  //           this.router.navigateByUrl('/usuarios/list');
  //         },
  //         error: (err) => {
  //           console.log('Erro ao incluir' + JSON.stringify(err));
  //         }
  //       });
  //     } else {
  //       this.usuarioService.update(usuario).subscribe({
  //         next: (UsuarioCadastrado) => {
  //           this.router.navigateByUrl('/usuarios/list');
  //         },
  //         error: (err) => {
  //           console.log('Erro ao alterar' + JSON.stringify(err));
  //         }
  //       });
  //     }
  //   }
  // }
  salvar() {
    if (this.formGroup.valid) {
      const usuario = this.formGroup.value;
  
      // Antes de enviar a requisição, adicione o telefone ao objeto do usuário
      usuario.telefones = this.telefonesFormArray.value;
  console.log(usuario.telefones);
      if (usuario.id == null) {
        this.usuarioService.save(usuario).subscribe({
          next: (usuarioCadastrado) => {
            this.router.navigateByUrl('/usuarios/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.usuarioService.update(usuario).subscribe({
          next: (UsuarioCadastrado) => {
            this.router.navigateByUrl('/usuarios/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });
      }
    }
  }
  
  excluir() {
    const usuario = this.formGroup.value;
    if (usuario.id != null) {
      this.usuarioService.delete(usuario).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/usuarios/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
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
