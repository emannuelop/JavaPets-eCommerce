import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estado } from '../models/estado.model';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<Estado[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Estado[]>(`${this.baseURL}/estados`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<Estado[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Estado[]>(`${this.baseURL}/estados/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<Estado> {
    return this.http.get<Estado>(`${this.baseURL}/estados/${id}`);
  }

  save(estado: Estado): Observable<Estado> {
    return this.http.post<Estado>(`${this.baseURL}/estados`, estado);
  }

  update(estado: Estado): Observable<Estado> {
    return this.http.put<Estado>(`${this.baseURL}/estados/${estado.id}`, estado );
  }

  delete(estado: Estado): Observable<any> {
    return this.http.delete<Estado>(`${this.baseURL}/estados/${estado.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/estados/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/estados/count/search/${nome}`);
  }

}
