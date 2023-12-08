// carrinho.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ItemCarrinho } from 'src/app/models/item-carrinho.interface';
import { ItemCompra } from 'src/app/models/itemCompra.model';
import { CarrinhoService } from 'src/app/services/carrinho.service';
import { CompraService } from 'src/app/services/compra.service';

@Component({
  selector: 'app-carrinho',
  templateUrl: './carrinho.component.html',
  styleUrls: ['./carrinho.component.css'],
})
export class CarrinhoComponent implements OnInit {
  carrinhoItens: ItemCarrinho[] = [];

  constructor(private carrinhoService: CarrinhoService,
              private router: Router,
              private compraService: CompraService) {}

  ngOnInit(): void {
    this.carrinhoService.carrinho$.subscribe(itens => {
      this.carrinhoItens = itens;
    });
  }

  removerItem(item: ItemCarrinho): void {
    this.carrinhoService.remover(item);
  }

  calcularTotal(): number {
    return this.carrinhoItens.reduce((total, item) => total + item.quantidade * item.preco, 0);
  }

  finalizarCompra() {
      this.compraService.insertIntoCarrrinho(this.carrinhoItens).subscribe(
        (response) => {
          this.carrinhoService.removerTudo();
          console.log('Item adicionado com sucesso!', response);
          console.log(this.carrinhoItens);
          this.router.navigateByUrl('user/compra/finalizar-compra');
        },
        (error) => {
          console.error('Erro ao adicionar item ao carrinho', error);
          // Trate o erro conforme necessário
        }
      );
  }
  
}