import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CupomDesconto } from 'src/app/models/cupomDesconto.model';
import { CupomDescontoService } from 'src/app/services/cupomDesconto.service';

@Component({
  selector: 'app-cupomDesconto-list',
  templateUrl: './cupomDesconto-list.component.html',
  styleUrls: ['./cupomDesconto-list.component.css']
})
export class CupomDescontoListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'codigoCupom-column', 'quantidadeDisponivel-column', 'porcentagemDesconto-column', 'acao-column'];
  cupomDescontos: CupomDesconto[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private cupomDescontoService: CupomDescontoService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.carregarCupomDescontos();
    this.carregarTotalRegistros();
  }

  carregarCupomDescontos() {
    if(this.filtro){
      this.cupomDescontoService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.cupomDescontos = data;
      })

    }else{

    this.cupomDescontoService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.cupomDescontos = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.cupomDescontoService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.cupomDescontoService.count().subscribe(data => {
      this.totalRegistros = data;
    });
    }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarCupomDescontos();
  }

  aplicarFiltro() {
    this.carregarCupomDescontos();
    this.carregarTotalRegistros();
  }

  openConfirmationDialog(cupomDesconto: CupomDesconto) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este cupom?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.cupomDescontoService.delete(cupomDesconto).subscribe({
            next: () => {
              this.cupomDescontos = this.cupomDescontos.filter(u => u !== cupomDesconto);
              this.carregarTotalRegistros();
              this.carregarCupomDescontos();
              console.log('cupom desconto excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir cupom desconto:', error);
            }
          });
        }
      }
    });
  }

}
