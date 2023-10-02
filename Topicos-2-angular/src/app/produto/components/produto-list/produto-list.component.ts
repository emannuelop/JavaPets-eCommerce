import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Produto } from 'src/app/models/produto.model';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent {
  
  tableColumns: string[] = ['id-column', 'nome-column', 'descricao-column', 'marca-column',
   'fornecedor-column', 'categoria-column', 'preco-column', 'estoque-column', 'acao-column'];
   
  produtos: Produto[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private produtoService: ProdutoService) {}

  ngOnInit(): void {
    this.carregarProdutos();
    this.carregarTotalRegistros();
  }

  carregarProdutos() {
    if(this.filtro){
      this.produtoService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.produtos = data;
      })

    }else{

    this.produtoService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.produtos = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.produtoService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.produtoService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarProdutos();
  }

  aplicarFiltro() {
    this.carregarProdutos();
    this.carregarTotalRegistros();
  }
}
