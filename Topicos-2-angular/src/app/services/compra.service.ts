import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Compra } from '../models/compra.model';
import { ItemCompra } from '../models/itemCompra.model';
import { ItemCarrinho } from '../models/item-carrinho.interface';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseURL: string =  'http://localhost:8080/compras';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getCompraEmAndamento(): Observable<Compra> {
    return this.http.get<Compra>(`${this.baseURL}/carrinho`);
  }

  insertIntoCarrrinho(itemCompra: ItemCarrinho): Observable<any> {

    // const headers = new HttpHeaders({
    //   'Content-Type': 'application/json',
    //   'Authorization': `Bearer ${this.authService.getToken}`,
    // });

    const obj = {
      idProduto: itemCompra.id,
      quantidade: itemCompra.quantidade
    }
    console.log(obj);
    return this.http.post(`${this.baseURL}` , obj);
  }

}
