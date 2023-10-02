import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Categoria } from 'src/app/models/categoria.model';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-categoria-list',
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.css']
})
export class CategoriaListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'descricao-column', 'acao-column'];
  categorias: Categoria[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.carregarCategorias();
    this.carregarTotalRegistros();
  }

  carregarCategorias() {
    if(this.filtro){
      this.categoriaService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.categorias = data;
      })

    }else{

    this.categoriaService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.categorias = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.categoriaService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.categoriaService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarCategorias();
  }

  aplicarFiltro() {
    this.carregarCategorias();
    this.carregarTotalRegistros();
  }

}
