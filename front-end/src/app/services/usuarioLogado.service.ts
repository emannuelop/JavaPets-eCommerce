// user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioLogadoService {
  private baseURL: string = 'http://localhost:8080/perfil'

  constructor(private http: HttpClient) {}

  getDadosPessoais(): Observable<any> {
    const url = `${this.baseURL}/dados-pessoais`;
    return this.http.get(url);
  }
}