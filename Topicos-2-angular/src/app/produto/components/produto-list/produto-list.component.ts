import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { Produto } from 'src/app/models/produto.model';
import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent {
  
  tableColumns: string[] = ['id-column', 'nome-column', 'descricao-column', 'marca-column',
   'fornecedor-column', 'categoria-column', 'preco-column', 'estoque-column', 'acao-column'];
   
  produtos: Produto[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private produtoService: ProdutoService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.carregarProdutos();
    this.carregarTotalRegistros();
  }

  carregarProdutos() {
    if(this.filtro){
      this.produtoService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.produtos = data;
      })

    }else{

    this.produtoService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.produtos = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.produtoService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.produtoService.count().subscribe(data => {
      this.totalRegistros = data;
    });
  }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarProdutos();
  }

  aplicarFiltro() {
    this.carregarProdutos();
    this.carregarTotalRegistros();
  }

  openConfirmationDialog(produto: Produto) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este produto?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.produtoService.delete(produto).subscribe({
            next: () => {
              this.produtos = this.produtos.filter(u => u !== produto);
              this.carregarTotalRegistros();
              this.carregarProdutos();
              console.log('Produto excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir produto:', error);
            }
          });
        }
      }
    });
  }

}
