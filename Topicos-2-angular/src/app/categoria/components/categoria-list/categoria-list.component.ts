import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
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

  constructor(private categoriaService: CategoriaService,private dialog:MatDialog) {}

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
<<<<<<< HEAD
=======

>>>>>>> c25503659db2fccd8979a8444c15bff7ffe31621
  openConfirmationDialog(categoria: Categoria) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir esta categoria?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.categoriaService.delete(categoria).subscribe({
            next: () => {
              this.categorias = this.categorias.filter(u => u !== categoria);
              this.carregarTotalRegistros();
              this.carregarCategorias();
<<<<<<< HEAD
              console.log('Usuário excluído com sucesso');
            },
            error: (response: HttpErrorResponse) => {
              if (response.status === 500) {
                alert("A categoria em questão já está sendo utilizada!!");
              } else {
                console.error('Erro ao excluir cidade:', response.error);
              }
=======
              console.log('Categoria excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir categoria:', error);
>>>>>>> c25503659db2fccd8979a8444c15bff7ffe31621
            }
          });
        }
      }
    });
  }
<<<<<<< HEAD
=======

>>>>>>> c25503659db2fccd8979a8444c15bff7ffe31621
}
