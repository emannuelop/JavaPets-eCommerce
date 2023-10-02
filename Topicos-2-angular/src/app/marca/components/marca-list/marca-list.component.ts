import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Marca } from 'src/app/models/marca.model';
import { MarcaService } from 'src/app/services/marca.service';

@Component({
  selector: 'app-marca-list',
  templateUrl: './marca-list.component.html',
  styleUrls: ['./marca-list.component.css']
})
export class MarcaListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'cnpj-column', 'acao-column'];
  marcas: Marca[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private marcaService: MarcaService) {}

  ngOnInit(): void {
    this.carregarMarcas();
    this.carregarTotalRegistros();
  }

  carregarMarcas() {
    if(this.filtro){
      this.marcaService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.marcas = data;
      })

    }else{

    this.marcaService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.marcas = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.marcaService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.marcaService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarMarcas();
  }

  aplicarFiltro() {
    this.carregarMarcas();
    this.carregarTotalRegistros();
  }

}
