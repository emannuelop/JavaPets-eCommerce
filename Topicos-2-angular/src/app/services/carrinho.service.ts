// carrinho.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LocalStorageService } from './local-storage-service';
import { ItemCarrinho } from '../models/item-carrinho.interface';

@Injectable({
  providedIn: 'root',
})
export class CarrinhoService {
  private carrinhoSubject = new BehaviorSubject<ItemCarrinho[]>([]);
  carrinho$ = this.carrinhoSubject.asObservable();

  constructor(private localStorageService: LocalStorageService) {
    const carrinhoArmazenado = localStorageService.getItem('carrinho') || [];
    this.carrinhoSubject.next(carrinhoArmazenado);
  }

  adicionar(faixa: ItemCarrinho): void {
    const carrinhoAtual = this.carrinhoSubject.value;
    const itemExistente = carrinhoAtual.find(item => item.id === faixa.id);

    if (itemExistente) {
      itemExistente.quantidade += faixa.quantidade || 1;
    } else {
      carrinhoAtual.push({ ...faixa });
    }

    this.carrinhoSubject.next(carrinhoAtual);
    this.atualizarArmazenamentoLocal();
  }

  removerTudo(): void {
    this.localStorageService.removeItem('carrinho');
    window.location.reload(); // reload na página
  }

  remover(item: ItemCarrinho): void {
    const carrinhoAtual = this.carrinhoSubject.value;
    const carrinhoAtualizado = carrinhoAtual.filter(itemCarrinho => itemCarrinho !== item);

    this.carrinhoSubject.next(carrinhoAtualizado);
    this.atualizarArmazenamentoLocal();
  }

  obter(): ItemCarrinho[] {
    return this.carrinhoSubject.value;
    
  }

  private atualizarArmazenamentoLocal(): void {
    localStorage.setItem('carrinho', JSON.stringify(this.carrinhoSubject.value));
  }
}