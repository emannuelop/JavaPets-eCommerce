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

  getCompras(): Observable<Compra[]> {

    return this.http.get<Compra[]>(`${this.baseURL}/historico-compras`);
  }

  insertIntoCarrrinho(itemCompra: ItemCarrinho[]): Observable<any> {

    // const headers = new HttpHeaders({
    //   'Content-Type': 'application/json',
    //   'Authorization': `Bearer ${this.authService.getToken}`,
    // });

    const itens = itemCompra.map(item => ({
      idProduto: item.id,
      quantidade: item.quantidade
    }));
    console.log(itens);
    return this.http.post(`${this.baseURL}` , itens);
  }

  pagarPix(): Observable<any> {

        const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken}`,
    });

    return this.http.patch(`${this.baseURL}/carrinho/pagar-pix`, headers);
  }

  pagarBoleto(): Observable<any> {

    const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${this.authService.getToken}`,
});

return this.http.patch(`${this.baseURL}/carrinho/pagar-boleto-bancario`, headers);
}

}
