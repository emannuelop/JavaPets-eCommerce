import { Component, OnInit, signal } from '@angular/core';
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

  cards = signal<Card[]> ([]);
  produtos: Produto[] = [];
  filtro: string = "";
  pageSize = 10;
  pagina = 0;
  constructor(private produtoService: ProdutoService) {}

  ngOnInit(): void {
    this.carregarProdutos();
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
