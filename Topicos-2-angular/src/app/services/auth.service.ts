import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LocalStorageService } from './local-storage-service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseURL: string = 'http://localhost:8080/auth';
  private tokenKey = 'jwt_token';
  private usuarioLogadoKey = 'usuario_logado';
  private usuarioLogadoSubject = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient, 
              private localStorageService: LocalStorageService, 
              private jwtHelper: JwtHelperService) {

    this.initUsuarioLogado();

  }

  private initUsuarioLogado() {
    const usuario = localStorage.getItem(this.usuarioLogadoKey);
    if (usuario) {
      const usuarioLogado = JSON.parse(usuario);

      this.setUsuarioLogado(usuarioLogado);
      this.usuarioLogadoSubject.next(usuarioLogado);
    }
  }


  login(email: string, senha: string): Observable<any> {
    const params = {
      login: email,
      senha: senha
    }

    //{ observe: 'response' } para garantir que a resposta completa seja retornada (incluindo o cabeçalho)
    return this.http.post(`${this.baseURL}`, params, {observe: 'response'}).pipe(
      tap((res: any) => {
        const authToken = res.headers.get('Authorization') ?? '';
        if (authToken) {
          this.setToken(authToken);
          const usuarioLogado = res.body;
          console.log(usuarioLogado, 'asdasd');
          if (usuarioLogado) {
            this.setUsuarioLogado(usuarioLogado);
            this.usuarioLogadoSubject.next(usuarioLogado);
          }
        }
      })
    );
  }

  setUsuarioLogado(usuario: Usuario): void {
    this.localStorageService.setItem(this.usuarioLogadoKey, usuario);
  }

  setToken(token: string): void {
    this.localStorageService.setItem(this.tokenKey, token);
  }

  getUsuarioLogado() {
    return this.usuarioLogadoSubject.asObservable();
  }

  getToken(): string | null {
    return this.localStorageService.getItem(this.tokenKey);
  }

  removeToken(): void {
    this.localStorageService.removeItem(this.tokenKey);
  }

  removeUsuarioLogado(): void {
    this.localStorageService.removeItem(this.usuarioLogadoKey);
    this.usuarioLogadoSubject.next(null);
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    // Verifica se o token é nulo ou está expirado
    return !token || this.jwtHelper.isTokenExpired(token);
    // npm install @auth0/angular-jwt
  }



}