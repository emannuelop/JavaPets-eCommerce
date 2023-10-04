import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { cupomDesconto } from '../models/cupomDesconto.model';

@Injectable({
  providedIn: 'root'
})
export class cupomDescontoService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<cupomDesconto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<cupomDesconto[]>(`${this.baseURL}/cupomDescontos`,{params});
  }

  findByNome(nome: string, pagina:number, tamanhoPagina:number): Observable<cupomDesconto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<cupomDesconto[]>(`${this.baseURL}/cupomDescontos/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<cupomDesconto> {
    return this.http.get<cupomDesconto>(`${this.baseURL}/cupomDescontos/${id}`);
  }

  save(cupomDesconto: cupomDesconto): Observable<cupomDesconto> {
    return this.http.post<cupomDesconto>(`${this.baseURL}/cupomDescontos`, cupomDesconto);
  }

  update(cupomDesconto: cupomDesconto): Observable<cupomDesconto> {
    return this.http.put<cupomDesconto>(`${this.baseURL}/cupomDescontos/${cupomDesconto.id}`, cupomDesconto );
  }

  delete(cupomDesconto: cupomDesconto): Observable<any> {
    return this.http.delete<cupomDesconto>(`${this.baseURL}/cupomDescontos/${cupomDesconto.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/cupomDescontos/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/cupomDescontos/count/search/${nome}`);
  }

}
