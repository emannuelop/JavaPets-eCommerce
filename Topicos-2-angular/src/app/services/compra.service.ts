import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Compra } from '../models/compra.model';
import { ItemCompra } from '../models/itemCompra.model';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}


  getCompraEmAndamento(): Observable<Compra> {
    return this.http.get<Compra>(`${this.baseURL}/compras/`);
  }

  carrinhoAdd(itemCompra: ItemCompra): Observable<ItemCompra> {
    const obj = {
      idProduto: itemCompra.produto.id,
      quantidade: itemCompra.quantidade
    }
    return this.http.post<ItemCompra>(`${this.baseURL}/compras/carrinho/adiconar-item`, obj);
  }

}
