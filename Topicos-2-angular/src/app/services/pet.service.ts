import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pet } from '../models/pet.model';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private baseURL: string =  'http://localhost:8080';

  constructor(private http: HttpClient) {}

  findAll(pagina:number, tamanhoPagina:number): Observable<Pet[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Pet[]>(`${this.baseURL}/pets`,{params});
  }

  findByNome(nome: string,pagina:number, tamanhoPagina:number): Observable<Pet[]> {
    const params = {
      page: pagina.toString(),
      pageSize: tamanhoPagina.toString()
    }
    return this.http.get<Pet[]>(`${this.baseURL}/pets/pets/${nome}`, {params});
  }

  findById(id: string): Observable<Pet> {
    return this.http.get<Pet>(`${this.baseURL}/pets/${id}`);
  }

  save(pet: Pet): Observable<Pet> {
    return this.http.post<Pet>(`${this.baseURL}/pets`, pet);
  }

  update(pet: Pet): Observable<Pet> {
    return this.http.put<Pet>(`${this.baseURL}/pets/${pet.id}`, pet );
  }

  delete(pet: Pet): Observable<any> {
    return this.http.delete<Pet>(`${this.baseURL}/pets/${pet.id}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/pets/count`);
  }

}
