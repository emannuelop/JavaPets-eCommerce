import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Estado } from 'src/app/models/estado.model';
import { EstadoService } from 'src/app/services/estado.service';

@Component({
  selector: 'app-estado-list',
  templateUrl: './estado-list.component.html',
  styleUrls: ['./estado-list.component.css']
})
export class EstadoListComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  tableColumns: string[] = ['id-column', 'nome-column', 'sigla-column', 'acao-column'];
  estados: Estado[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private estadoService: EstadoService, private dialog: MatDialog,
  private customPaginatorIntl: CustomPaginatorIntl) {}

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

  ngOnInit(): void {
    this.carregarEstados();
    this.carregarTotalRegistros();
  }

  carregarEstados() {
    if(this.filtro){
      this.estadoService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.estados = data;
      })

    }else{

    this.estadoService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.estados = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.estadoService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.estadoService.count().subscribe(data => {
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

  openConfirmationDialog(estado: Estado) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este estado?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.estadoService.delete(estado).subscribe({
            next: () => {
              this.estados = this.estados.filter(u => u !== estado);
              this.carregarTotalRegistros();
              this.carregarEstados();
              console.log('Estado excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir estado:', error);
            }
          });
        }
      }
    });
  }

}
