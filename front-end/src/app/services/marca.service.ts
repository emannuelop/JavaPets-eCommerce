import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../models/marca.model';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<Marca[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Marca[]>(`${this.baseURL}/marcas`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<Marca[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Marca[]>(`${this.baseURL}/marcas/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<Marca> {
    return this.http.get<Marca>(`${this.baseURL}/marcas/${id}`);
  }

  save(marca: Marca): Observable<Marca> {
    return this.http.post<Marca>(`${this.baseURL}/marcas`, marca);
  }

  update(marca: Marca): Observable<Marca> {
    return this.http.put<Marca>(`${this.baseURL}/marcas/${marca.id}`, marca );
  }

  delete(marca: Marca): Observable<any> {
    return this.http.delete<Marca>(`${this.baseURL}/marcas/${marca.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/marcas/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/marcas/count/search/${nome}`);
  }

}
