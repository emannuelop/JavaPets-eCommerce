import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { Cidade } from 'src/app/models/cidade.model';
import { CidadeService } from 'src/app/services/cidade.service';

@Component({
  selector: 'app-cidade-list',
  templateUrl: './cidade-list.component.html',
  styleUrls: ['./cidade-list.component.css']
})
export class CidadeListComponent {

  tableColumns: string[] = ['id-column', 'nome-column', 'estado-column', 'acao-column'];
  cidades: Cidade[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private cidadeService: CidadeService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.carregarCidades();
    this.carregarTotalRegistros();
  }

  carregarCidades() {
    if(this.filtro){
      this.cidadeService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.cidades = data;
      })

    }else{

    this.cidadeService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.cidades = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.cidadeService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.cidadeService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarCidades();
  }

  aplicarFiltro() {
    this.carregarCidades();
    this.carregarTotalRegistros();
  }

  openConfirmationDialog(cidade: Cidade) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir esta cidade?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.cidadeService.delete(cidade).subscribe({
            next: () => {
              this.cidades = this.cidades.filter(u => u !== cidade);
              this.carregarTotalRegistros();
              this.carregarCidades();
              console.log('Usuário excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir usuário:', error);
            }
          });
        }
      }
    });
  }
}
