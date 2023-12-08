import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { Compra } from 'src/app/models/compra.model';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { CompraService } from 'src/app/services/compra.service';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit{


  tableColumns: string[] = ['id-column', 'totalCompra-column'];
  compras: Compra[] = [];


  constructor(private compraService: CompraService) {}

  ngOnInit(): void {
    this.carregarCompras();
  }

  carregarCompras() {
      this.compraService.getCompras().subscribe(data => {
        this.compras = data;
      })
  }


}
