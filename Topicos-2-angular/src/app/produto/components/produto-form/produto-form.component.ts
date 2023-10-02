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
  marcas: Marca[] = [];
  fornecedores: Fornecedor[] = [];
  categorias: Categoria[] = [];

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
    
  }

  initializeForm() {
    const produto: Produto = this.activatedRoute.snapshot.data['produto'];

    // selecionando 
    const marca = this.marcas.find(marca => marca.id === (produto?.marca?.id || null));

    const fornecedor = this.fornecedores.find(fornecedor => fornecedor.id === (produto?.fornecedor?.id || null));

    const categoria = this.categorias.find(categoria => categoria.id === (produto?.categoria?.id || null));

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
            this.router.navigateByUrl('/produtos/list');
          },
          error: (err) => {
            console.log('Erro ao incluir' + JSON.stringify(err));
          }
        });
      } else {
        this.produtoService.update(produto).subscribe({
          next: (produtoCadastrado) => {
            this.router.navigateByUrl('/produtos/list');
          },
          error: (err) => {
            console.log('Erro ao alterar' + JSON.stringify(err));
          }
        });        
      }
    }
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

}
