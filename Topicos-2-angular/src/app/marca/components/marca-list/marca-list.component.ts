import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Marca } from 'src/app/models/marca.model';
import { MarcaService } from 'src/app/services/marca.service';

@Component({
  selector: 'app-marca-list',
  templateUrl: './marca-list.component.html',
  styleUrls: ['./marca-list.component.css']
})

export class MarcaListComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;


  tableColumns: string[] = ['id-column', 'nome-column', 'cnpj-column', 'acao-column'];
  marcas: Marca[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private marcaService: MarcaService, private dialog: MatDialog, private customPaginatorIntl: CustomPaginatorIntl) {}

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

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

  openConfirmationDialog(marca: Marca) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir esta marca?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.marcaService.delete(marca).subscribe({
            next: () => {
              this.marcas = this.marcas.filter(u => u !== marca);
              this.carregarTotalRegistros();
              this.carregarMarcas();
              console.log('Marca excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir marca:', error);
            }
          });
        }
      }
    });
  }

}
