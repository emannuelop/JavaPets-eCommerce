import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Fornecedor } from 'src/app/models/fornecedor.model';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-fornecedor-list',
  templateUrl: './fornecedor-list.component.html',
  styleUrls: ['./fornecedor-list.component.css']
})
export class FornecedorListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'acao-column'];
  fornecedores: Fornecedor[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private fornecedorService: FornecedorService) {}

  ngOnInit(): void {
    this.carregarEstados();
    this.carregarTotalRegistros();
  }

  carregarEstados() {
    if(this.filtro){
      this.fornecedorService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.fornecedores = data;
      })

    }else{

    this.fornecedorService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.fornecedores = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.fornecedorService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.fornecedorService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarEstados();
  }

  aplicarFiltro() {
    this.carregarEstados();
    this.carregarTotalRegistros();
  }

}