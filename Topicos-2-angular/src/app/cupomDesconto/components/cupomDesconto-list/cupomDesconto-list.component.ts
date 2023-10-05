import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
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

  constructor(private cupomDescontoService: CupomDescontoService) {}

  ngOnInit(): void {
    this.carregarCupomDescontos();
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

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarCupomDescontos();
  }

  aplicarFiltro() {
    this.carregarCupomDescontos();
  }

}
