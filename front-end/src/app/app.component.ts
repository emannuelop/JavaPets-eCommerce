import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { LocalStorageService } from './services/local-storage-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-topicos2-2023-2';
  currentPage: string;
  constructor(private router: Router, private authService: AuthService,  private localStorageService: LocalStorageService,) {  this.currentPage = '';}
  
  ngOnInit() {
    if (!this.authService.isTokenExpired()){
      this.currentPage = this.router.url;
     console.log(this.currentPage);
     const usuarioLogado = this.localStorageService.getItem('usuario_logado');
     if(this.currentPage === "/" && usuarioLogado.perfis.length === 1 ){
     
      this.router.navigateByUrl("/user/home/home-page")
     }
     console.log(this.router.url)
    }else{
      this.router.navigateByUrl('/auth/login');
    this.currentPage = this.router.url;
    }
  }
  ngAfterViewInit() {
    this.currentPage = this.router.url;
  }
}
