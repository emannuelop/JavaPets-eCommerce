import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CupomDesconto } from '../models/cupomDesconto.model';

@Injectable({
  providedIn: 'root'
})
export class CupomDescontoService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<CupomDesconto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<CupomDesconto[]>(`${this.baseURL}/cupomDescontos`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<CupomDesconto[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<CupomDesconto[]>(`${this.baseURL}/cupomDescontos/searchByNome/${nome}`, {params});
  }

  findById(id: string): Observable<CupomDesconto> {
    return this.http.get<CupomDesconto>(`${this.baseURL}/cupomDescontos/${id}`);
  }

  save(cupomDesconto: CupomDesconto): Observable<CupomDesconto> {
    return this.http.post<CupomDesconto>(`${this.baseURL}/cupomDescontos`, cupomDesconto);
  }

  update(cupomDesconto: CupomDesconto): Observable<CupomDesconto> {
    return this.http.put<CupomDesconto>(`${this.baseURL}/cupomDescontos/${cupomDesconto.id}`, cupomDesconto );
  }

  delete(cupomDesconto: CupomDesconto): Observable<any> {
    return this.http.delete<CupomDesconto>(`${this.baseURL}/cupomDescontos/${cupomDesconto.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/cupomDescontos/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/cupomDescontos/count/search/${nome}`);
  }

}
