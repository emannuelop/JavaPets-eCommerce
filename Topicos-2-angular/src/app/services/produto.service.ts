import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto.model';


@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private baseURL: string =  'http://localhost:8080/produtos';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<Produto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Produto[]>(`${this.baseURL}`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<Produto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Produto[]>(`${this.baseURL}/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<Produto> {
    return this.http.get<Produto>(`${this.baseURL}/${id}`);
  }

  save(produto: Produto): Observable<Produto> {
    const obj = {
      nome: produto.nome,
      descricao: produto.descricao,
      idMarca: produto.marca.id,
      idFornecedor: produto.fornecedor.id,
      idCategoria: produto.categoria.id,
      preco: produto.preco,
      estoque: produto.estoque  
    }
    return this.http.post<Produto>(`${this.baseURL}`, obj);
  }

  update(produto: Produto): Observable<Produto> {
    const obj = {
        nome: produto.nome,
        descricao: produto.descricao,
        idMarca: produto.marca.id,
        idFornecedor: produto.fornecedor.id,
        idCategoria: produto.categoria.id,
        preco: produto.preco,
        estoque: produto.estoque  
      }
    return this.http.put<Produto>(`${this.baseURL}/${produto.id}`, obj );
  }

  delete(produto: Produto): Observable<any> {
    return this.http.delete<Produto>(`${this.baseURL}/${produto.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/count/search/${nome}`);
  }

  getUrlImagem(nomeImagem: string): string {
    return `${this.baseURL}/image/download/${nomeImagem}`;
  }

}
