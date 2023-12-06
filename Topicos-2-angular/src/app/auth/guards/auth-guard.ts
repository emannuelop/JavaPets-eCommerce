import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard  {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
    authService: AuthService
  ): boolean {
    // Verifica se o token está presente e é válido
    if (this.authService.isTokenExpired()) {
      // Se o token não está presente ou é inválido, redirecione para a página de login
      this.authService.removeToken();
      return false;
    }

    // Se o token está presente e é válido, permita o acesso à rota
    return true;
  }
}
