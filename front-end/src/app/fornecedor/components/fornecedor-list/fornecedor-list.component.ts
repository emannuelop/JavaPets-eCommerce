import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Fornecedor } from 'src/app/models/fornecedor.model';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-fornecedor-list',
  templateUrl: './fornecedor-list.component.html',
  styleUrls: ['./fornecedor-list.component.css']
})
export class FornecedorListComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  tableColumns: string[] = ['id-column', 'nome-column', 'email-column', 'acao-column'];
  fornecedores: Fornecedor[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private fornecedorService: FornecedorService, private dialog: MatDialog, private customPaginatorIntl: CustomPaginatorIntl) {}

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

  ngOnInit(): void {
    this.carregarFornecedores();
    this.carregarTotalRegistros();
  }

  carregarFornecedores() {
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
    this.carregarFornecedores();
  }

  aplicarFiltro() {
    this.carregarFornecedores();
    this.carregarTotalRegistros();
  }

  openConfirmationDialog(fornecedor: Fornecedor) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este fornecedor?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.fornecedorService.delete(fornecedor).subscribe({
            next: () => {
              this.fornecedores = this.fornecedores.filter(u => u !== fornecedor);
              this.carregarTotalRegistros();
              this.carregarFornecedores();
              console.log('Fornecedor excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir fornecedor:', error);
            }
          });
        }
      }
    });
  }

}