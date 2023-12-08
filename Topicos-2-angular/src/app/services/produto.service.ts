import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto.model';


@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private baseURL: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  findAll(pagina: number, tamanhoPagina: number): Observable<Produto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Produto[]>(`${this.baseURL}/produtos`, { params });
  }

  findByNome(nome: string, pagina: number, tamanhoPagina: number): Observable<Produto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Produto[]>(`${this.baseURL}/produtos/searchByNome/${nome}`, { params });
  }

  findById(id: string): Observable<Produto> {
    return this.http.get<Produto>(`${this.baseURL}/produtos/${id}`);
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
    return this.http.post<Produto>(`${this.baseURL}/produtos`, obj);
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
    return this.http.put<Produto>(`${this.baseURL}/produtos/${produto.id}`, obj);
  }

  delete(produto: Produto): Observable<any> {
    return this.http.delete<Produto>(`${this.baseURL}/produtos/${produto.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/produtos/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/produtos/count/search/${nome}`);
  }

  getUrlImagem(nomeImagem: string): string {
    return `${this.baseURL}/produtos/image/download/${nomeImagem}`;
  }

  uploadImagem(id: number, nomeImagem: string, imagem: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('id', id.toString());
    formData.append('nomeImagem', imagem.name);
    formData.append('imagem', imagem, imagem.name);
    console.log(formData.get('nomeImagem'));

    return this.http.patch<Produto>(`${this.baseURL}/produtos/image/upload`, formData);
  }

  getRelatorio(): Observable<any> {
    const url = `${this.baseURL}/produtos/relatorios`;

    const headers = new HttpHeaders({
      'Content-Type': 'application/json', // Change Content-Type to application/json
      'Accept': 'application/pdf'
    });

    const options = {
      headers: headers,
      responseType: 'arraybuffer' as 'json'
    };

    return this.http.get(url, options);
  }

}
