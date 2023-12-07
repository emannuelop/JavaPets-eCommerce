import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-user-template',
  templateUrl: './user-template.component.html',
  styleUrls: ['./user-template.component.css']
})
export class UserTemplateComponent {
isLoginPage: boolean = false;
constructor(private router: Router){
  this.router.events.subscribe((event) => {
    if (event instanceof NavigationEnd) {
      // Atualiza o status de isLoginPage com base na rota atual
      this.isLoginPage = event.url === '/user/auth/login';
    }
  });
}
}
