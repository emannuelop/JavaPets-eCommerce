import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario.model';
import { SidebarService } from '../../services/sidebar.service';
import { AuthService } from 'src/app/services/auth.service';
import { LocalStorageService } from 'src/app/services/local-storage-service';
import { Subscription } from 'rxjs';
import { CarrinhoService } from 'src/app/services/carrinho.service';

@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrls: ['./header-user.component.css']
})
export class HeaderUserComponent implements OnInit {

  qtdItensCarrinho:number = 0;

  usuarioLogado: Usuario | null = null;
  private subscription = new Subscription();

  constructor(private sidebarService: SidebarService,
    private authService: AuthService,
    private carrinhoService: CarrinhoService,
    private localStorageService: LocalStorageService) {  }

  clickMenu() {
    this.sidebarService.toggle();
  }

  ngOnInit(): void {
    this.obterQtdItensCarrinho();
    this.obterUsuarioLogado();   
}

obterQtdItensCarrinho() {
  this.carrinhoService.carrinho$.subscribe(itens => {
    this.qtdItensCarrinho = itens.length
  });
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
