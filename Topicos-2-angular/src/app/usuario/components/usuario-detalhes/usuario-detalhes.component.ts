import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Usuario } from 'src/app/models/usuario.model';
import { UsuarioLogado } from 'src/app/models/usuariologado.model';
import { AuthService } from 'src/app/services/auth.service';
import { UsuarioLogadoService } from 'src/app/services/usuarioLogado.service';

@Component({
  selector: 'app-usuario-detalhes',
  templateUrl: './usuario-detalhes.component.html',
  styleUrls: ['./usuario-detalhes.component.css']
})
export class UsuarioDetalhesComponent {
  usuarioLogado: UsuarioLogado | null = null;
  private subscription = new Subscription();

  constructor(private authService: AuthService, private usuarioLogadoService: UsuarioLogadoService) {}

  ngOnInit(): void {
    this.getDadosPessoais();
   
  }

  getDadosPessoais() {
    this.usuarioLogadoService.getDadosPessoais().subscribe(
      (dadosPessoais: any) => {
        this.usuarioLogado = dadosPessoais;
      },
      (error) => {
        console.error('Erro ao obter dados pessoais:', error);
      }
    );
  }

  editar() {
    // Adicione a lógica para editar o usuário, se necessário
  }
}
