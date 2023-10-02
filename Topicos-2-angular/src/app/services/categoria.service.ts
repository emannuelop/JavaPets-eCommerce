import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from '../models/categoria.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<Categoria[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Categoria[]>(`${this.baseURL}/categorias`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<Categoria[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Categoria[]>(`${this.baseURL}/categorias/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.baseURL}/categorias/${id}`);
  }

  save(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(`${this.baseURL}/categorias`, categoria);
  }

  update(categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.baseURL}/categorias/${categoria.id}`, categoria );
  }

  delete(categoria: Categoria): Observable<any> {
    return this.http.delete<Categoria>(`${this.baseURL}/categorias/${categoria.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/categorias/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/categorias/count/search/${nome}`);
  }

}
