import { Component, OnInit } from '@angular/core';
import { SidebarService } from '../../services/sidebar.service';
import { AuthService } from 'src/app/services/auth.service';
import { LocalStorageService } from 'src/app/services/local-storage-service';
import { Usuario } from 'src/app/models/usuario.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  usuarioLogado: Usuario | null = null;
  private subscription = new Subscription();

  constructor(private sidebarService: SidebarService,
    private authService: AuthService,
    private localStorageService: LocalStorageService) {  }

  clickMenu() {
    this.sidebarService.toggle();
  }

  ngOnInit(): void {
    this.obterUsuarioLogado();   
}

  obterUsuarioLogado() {
    this.subscription.add(this.authService.getUsuarioLogado().subscribe(
      usuario => this.usuarioLogado = usuario
    ));
  }

  deslogar() {
    this.authService.removeToken()
    this.authService.removeUsuarioLogado();
  }

}
