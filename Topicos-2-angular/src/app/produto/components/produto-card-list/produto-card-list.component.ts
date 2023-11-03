import { Component, OnInit, ViewChild, signal } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Produto } from 'src/app/models/produto.model';
import { ProdutoService } from 'src/app/services/produto.service';

type Card = {
  titulo: string;
  preco: number;
  urlImagem: string;
}

@Component({
  selector: 'app-produto-card-list',
  templateUrl: './produto-card-list.component.html',
  styleUrls: ['./produto-card-list.component.css']
})
export class ProdutoCardListComponent implements OnInit  {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  cards = signal<Card[]> ([]);
  produtos: Produto[] = [];
  totalRegistros = 0;
  filtro: string = "";
  pageSize = 10;
  pagina = 0;
  constructor(private produtoService: ProdutoService, private customPaginatorIntl: CustomPaginatorIntl) {}

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

  ngOnInit(): void {
    this.carregarProdutos();
    this.carregarTotalRegistros();
  }

  carregarProdutos() {
    if(this.filtro){
      this.produtoService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.produtos = data;
        this.carregarCards();
      })

    }else{

    this.produtoService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.produtos = data;
      this.carregarCards();
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

  carregarCards() {
    const cards: Card[] = [];
    this.produtos.forEach(produto => {
      cards.push({
        titulo: produto.nome,
        preco: produto.preco,
        urlImagem: this.produtoService.getUrlImagem(produto.nomeImagem)
      });
    });
    this.cards.set(cards);
  }

  aplicarFiltro() {
    this.carregarProdutos();
  
  }

}
