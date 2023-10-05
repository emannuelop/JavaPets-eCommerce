import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ConfimationDialogComponent } from 'src/app/confimation-dialog/confimation-dialog.component';
import { Usuario } from 'src/app/models/usuario.model'; // Importe a classe Usuario
import { UsuarioService } from 'src/app/services/usuario.service'; // Importe o serviço de usuário

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent {

  tableColumns: string[] = ['login-column', 'nome-column', 'cpf-column', 'email-column', 'sexo-column', 'endereco-column', 'telefone-column', 'acao-column'];

  usuarios: Usuario[] = [];
  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";

  constructor(private usuarioService: UsuarioService, private dialog: MatDialog) { }

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

  // Função para abrir o diálogo de confirmação
  openConfirmationDialog(usuario: Usuario): void {
    const dialogRef = this.dialog.open(ConfimationDialogComponent, {
      width: '400px',
      data: { message: 'Tem certeza de que deseja excluir este usuário?' }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Assine o Observable retornado pelo serviço
        this.usuarioService.delete(usuario).subscribe(
          () => {
            // Executa esta parte após a exclusão bem-sucedida
            this.usuarios = this.usuarios.filter(u => u !== usuario)
            this.carregarTotalRegistros()
           this.carregarUsuarios()
            console.log('Usuário excluído com sucesso');
            // Adicione aqui a lógica para atualizar a lista de usuários após a exclusão
          },
          (error) => {
            // Trate os erros aqui
            console.error('Erro ao excluir usuário:', error);
          }
        );
      }
    });
  }
  
}

