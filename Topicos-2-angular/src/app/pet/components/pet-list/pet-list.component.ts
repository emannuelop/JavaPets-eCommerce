import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Pet } from 'src/app/models/pet.model';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.css']
})
export class PetListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'especie-column', 'raca-column', 'idadeEmMeses-column', 'acao-column'];
  pets: Pet[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private petService: PetService) {}

  ngOnInit(): void {
    this.carregarPets();
  }

  carregarPets() {
    if(this.filtro){
      this.petService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.pets = data;
      })

    }else{

    this.petService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.pets = data;
    })
  };
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarPets();
  }

  aplicarFiltro() {
    this.carregarPets();
  }

}
