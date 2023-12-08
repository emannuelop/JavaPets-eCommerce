import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Pet } from 'src/app/models/pet.model';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.css']
})
export class PetListComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  tableColumns: string[] = ['id-column', 'nome-column', 'especie-column', 'raca-column', 'idadeEmMeses-column', 'acao-column'];
  pets: Pet[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private petService: PetService, private dialog: MatDialog, private customPaginatorIntl: CustomPaginatorIntl) {}

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

  ngOnInit(): void {
    this.carregarPets();
    this.carregarTotalRegistros();
  }

  carregarPets() {
    if(this.filtro){
      this.petService.findByNome(this.filtro,this.pagina, this.pageSize).subscribe(data => {
        this.pets = data;
      })

    }else{

    this.petService.findAll(this.pagina, this.pageSize).subscribe(data => {
      this.pets = data;
    })
  };
  }

  carregarTotalRegistros() {
    if(this.filtro){
      this.petService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;})
    }else{

    this.petService.count().subscribe(data => {
      this.totalRegistros = data;
    });
    }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarPets();
  }

  aplicarFiltro() {
    this.carregarPets();
    this.carregarTotalRegistros();
  }

  openConfirmationDialog(pet: Pet) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este pet?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.petService.delete(pet).subscribe({
            next: () => {
              this.pets = this.pets.filter(u => u !== pet);
              this.carregarTotalRegistros();
              this.carregarPets();
              console.log('Pet excluído com sucesso');
            },
            error: (error) => {
              console.error('Erro ao excluir pet:', error);
            }
          });
        }
      }
    });
  }

}
