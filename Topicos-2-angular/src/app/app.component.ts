import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-topicos2-2023-2';
  currentPage: string;
  constructor(private router: Router, private authService: AuthService) {  this.currentPage = '';}
  
  ngOnInit() {
    if (!this.authService.isTokenExpired()){
      this.currentPage = this.router.url;

    }else{
      this.router.navigateByUrl('/auth/login');
    this.currentPage = this.router.url;
    }
  }
  ngAfterViewInit() {
    this.currentPage = this.router.url;
  }
}
