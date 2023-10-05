import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private baseURL: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  findAll(pagina: number, tamanhoPagina: number): Observable<Usuario[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Usuario[]>(`${this.baseURL}/usuarios`, { params });
  }

  findByNome(nome: string, pagina: number, tamanhoPagina: number): Observable<Usuario[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Usuario[]>(`${this.baseURL}/usuarios/searchByNome/${nome}`, { params });
  }

  findById(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseURL}/usuarios/${id}`);
  }

  save(usuario: Usuario): Observable<Usuario> {
    const obj = {
      login: usuario.login,
      senha: usuario.senha,
      pessoaFisicaDto: {
        nome: usuario.pessoaFisicaDto.nome,
        cpf: usuario.pessoaFisicaDto.cpf,
        email: usuario.pessoaFisicaDto.email,
        sexo: usuario.pessoaFisicaDto.sexo
      },
      endereco: {
        logradouro: usuario.endereco.logradouro,
        bairro: usuario.endereco.bairro,
        numero: usuario.endereco.numero,
        complemento: usuario.endereco.complemento,
        cep: usuario.endereco.cep,
        idMunicipio: usuario.endereco.idMunicipio
      },
       telefones: usuario.telefones.map(telefone =>({
         codigoArea: telefone.codigoDeArea,
         numero: telefone.numero
       }))
    }
    return this.http.post<Usuario>(`${this.baseURL}/usuarios`, obj);
  }

  update(usuario: Usuario): Observable<Usuario> {
    const obj = {
      login: usuario.login,
      senha: usuario.senha,

      pessoaFisicaDto: {
        nome: usuario.pessoaFisicaDto.nome,
        cpf: usuario.pessoaFisicaDto.cpf,
        email: usuario.pessoaFisicaDto.email,
        sexo: usuario.pessoaFisicaDto.sexo
      },

      endereco: {
        logradouro: usuario.endereco.logradouro,
        bairro: usuario.endereco.bairro,
        numero: usuario.endereco.numero,
        complemento: usuario.endereco.complemento,
        cep: usuario.endereco.cep,
        idMunicipio: usuario.endereco.idMunicipio
      },
      telefones: usuario.telefones.map(telefone => ({
        codigoArea: telefone.codigoDeArea,
        numero: telefone.numero
      }))
    }
    return this.http.put<Usuario>(`${this.baseURL}/usuarios/${usuario.id}`, obj);
  }

  delete(usuario: Usuario): Observable<any> {
    return this.http.delete<Usuario>(`${this.baseURL}/usuarios/${usuario.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/usuarios/count`);
  }

  countByNome(nome: string): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/usuarios/count/search/${nome}`);
  }

}