import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/models/categoria.model';
import { Fornecedor } from 'src/app/models/fornecedor.model';
import { Marca } from 'src/app/models/marca.model';
import { Produto } from 'src/app/models/produto.model';
import { CategoriaService } from 'src/app/services/categoria.service';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { MarcaService } from 'src/app/services/marca.service';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produto-form',
  templateUrl: './produto-form.component.html',
  styleUrls: ['./produto-form.component.css']
})
export class ProdutoFormComponent implements OnInit {
  formGroup: FormGroup;
  apiResponse: any = null;
  marcas: Marca[] = [];
  fornecedores: Fornecedor[] = [];
  categorias: Categoria[] = [];

  fileName: string = '';
  selectedFile: File | null = null; 
  imagePreview: string | ArrayBuffer | null = null;

  constructor(private formBuilder: FormBuilder,
              private marcaService: MarcaService,
              private fornecedorService: FornecedorService,
              private categoriaService: CategoriaService,
              private produtoService: ProdutoService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

              this.formGroup = this.formBuilder.group({
                id:[null],
                nome:[ '', Validators.required],
                descricao:[ '', Validators.required],
                marca: [null],
                fornecedor: [null],
                categoria: [null],
                preco:[null],
                estoque:[null]
              })

  }

  ngOnInit(): void {

    // buscando todos para o select
    this.marcaService.findAll(0, 20).subscribe(data => {
      this.marcas = data;
     this.initializeForm();
    });

    this.fornecedorService.findAll(0,20).subscribe(data => {
      this.fornecedores = data;
     this.initializeForm();
    });

    this.categoriaService.findAll(0,20).subscribe(data => {
      this.categorias = data;
     this.initializeForm();
    });
    // this.initializeForm()
  }

  initializeForm() {
    const produto: Produto = this.activatedRoute.snapshot.data['produto'];

    // selecionando 
    const marca = this.marcas.find(marca => marca.id === (produto?.marca?.id || null));

    const fornecedor = this.fornecedores.find(fornecedor => fornecedor.id === (produto?.fornecedor?.id || null));

    const categoria = this.categorias.find(categoria => categoria.id === (produto?.categoria?.id || null));

        // carregando a imagem do preview
        if (produto && produto.nomeImagem) {
          this.imagePreview = this.produtoService.getUrlImagem(produto.nomeImagem);
          this.fileName = produto.nomeImagem;
        }

    this.formGroup = this.formBuilder.group({
      id:[(produto && produto.id) ? produto.id : null],
      nome:[(produto && produto.nome) ? produto.nome : '', Validators.required],
      descricao:[(produto && produto.descricao) ? produto.descricao : '', Validators.required],
      marca:[marca],
      fornecedor:[fornecedor],
      categoria:[categoria],
      preco:[(produto && produto.preco) ? produto.preco : null],
      estoque:[(produto && produto.estoque) ? produto.estoque : null]
    })
    console.log(this.formGroup.value);
  }

  salvar() {
    if (this.formGroup.valid) {
      const produto = this.formGroup.value;
      if (produto.id == null) {
        this.produtoService.save(produto).subscribe({
          next: (produtoCadastrado) => {
            this.uploadImage(produtoCadastrado.id);

          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('descricao')?.setErrors({ apiError: this.getErrorMessage('descricao') });
           this.formGroup.get('preco')?.setErrors({ apiError: this.getErrorMessage('preco') });
           this.formGroup.get('estoque')?.setErrors({ apiError: this.getErrorMessage('estoque') });
     
           console.log('Erro ao incluir' + JSON.stringify(errorResponse));
         }
        });
      } else {
        this.produtoService.update(produto).subscribe({
          next: (produtoAtualizado) => {
            console.log('Id :' + produto.id);
            this.uploadImage(produto.id);
          },
          error: (errorResponse) => {
            // Processar erros da API
           this.apiResponse = errorResponse.error; 

           // Associar erros aos campos do formulário
           this.formGroup.get('nome')?.setErrors({ apiError: this.getErrorMessage('nome') });
           this.formGroup.get('descricao')?.setErrors({ apiError: this.getErrorMessage('descricao') });
           this.formGroup.get('preco')?.setErrors({ apiError: this.getErrorMessage('preco') });
           this.formGroup.get('estoque')?.setErrors({ apiError: this.getErrorMessage('estoque') });
     
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
    const produto = this.formGroup.value;
    if (produto.id != null) {
      this.produtoService.delete(produto).subscribe({
        next: (e) => {
          this.router.navigateByUrl('/produtos/list');
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      });
    }      
  }

  carregarImagemSelecionada(event: any) {
    this.selectedFile = event.target.files[0];

    if (this.selectedFile) {
      this.fileName = this.selectedFile.name;
      // carregando image preview
      const reader = new FileReader();
      reader.onload = e => this.imagePreview = reader.result;
      reader.readAsDataURL(this.selectedFile);
    }

  }

  private uploadImage(produtoId: number) {
    if (this.selectedFile) {
      this.produtoService.uploadImagem(produtoId, this.selectedFile.name, this.selectedFile)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/produtos/list');
        },
        error: err => {
          console.log('Erro ao fazer o upload da imagem');
          // tratar o erro
        }
      })
    } else {
      this.router.navigateByUrl('/produtos/list');
    }
  }

}
