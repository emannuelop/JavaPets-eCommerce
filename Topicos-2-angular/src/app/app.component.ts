import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-topicos2-2023-2';
  currentPage: string;
  constructor(private router: Router) {  this.currentPage = '';}
  
  ngOnInit() {
    this.router.navigateByUrl('/auth/login');
    this.currentPage = this.router.url;
  }
  ngAfterViewInit() {
    this.currentPage = this.router.url;
  }
}
