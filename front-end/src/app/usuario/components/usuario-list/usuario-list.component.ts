import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { CustomPaginatorIntl } from 'src/app/models/custom-paginator-intl';
import { Usuario } from 'src/app/models/usuario.model'; // Importe a classe Usuario
import { UsuarioService } from 'src/app/services/usuario.service'; // Importe o serviço de usuário

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent {

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | undefined;

  tableColumns: string[] = ['login-column', 'nome-column', 'cpf-column', 'email-column', 'sexo-column', 'endereco-column', 'telefone-column', 'acao-column'];

  usuarios: Usuario[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private usuarioService: UsuarioService, private dialog: MatDialog, private customPaginatorIntl: CustomPaginatorIntl) { }

  ngAfterViewInit() {
    if (this.paginator) {
      this.paginator._intl = this.customPaginatorIntl; // Configuração da internacionalização
    }
  }

  ngOnInit(): void {
    this.carregarUsuarios();
    this.carregarTotalRegistros();
  }

  carregarUsuarios() {
    if (this.filtro) {
      this.usuarioService.findByNome(this.filtro, this.pagina, this.pageSize).subscribe(data => {
        this.usuarios = data;
      });
    } else {
      this.usuarioService.findAll(this.pagina, this.pageSize).subscribe(data => {
        this.usuarios = data;
      });
    }
  }

  carregarTotalRegistros() {
    if (this.filtro) {
      this.usuarioService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;
      });
    } else {
      this.usuarioService.count().subscribe(data => {
        this.totalRegistros = data;
      });
    }
  }

  // Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarUsuarios();
  }

  aplicarFiltro() {
    this.carregarUsuarios();
    this.carregarTotalRegistros();
  }

  formatarCodigoDeArea(codigoDeArea: string): string {

    if (codigoDeArea.startsWith('0') && codigoDeArea.length > 1) {
      return `(${codigoDeArea.slice(1)})`;
    }
    return codigoDeArea;
  }

  
  openConfirmationDialog(usuario: Usuario) {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este usuário?' }
    });
  
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.usuarioService.delete(usuario).subscribe({
            next: () => {
              this.usuarios = this.usuarios.filter(u => u !== usuario);
              this.carregarTotalRegistros();
              this.carregarUsuarios();
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

