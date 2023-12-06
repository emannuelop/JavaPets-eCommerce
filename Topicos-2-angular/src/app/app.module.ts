import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { ConfimationDialogComponent } from './confimation-dialog/confimation-dialog.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { registerLocaleData } from '@angular/common';
import { JwtModule, JwtHelperService } from '@auth0/angular-jwt';
import localePt from '@angular/common/locales/pt';
import { AuthInterceptor } from './auth/interceptors/auth.interceptor';
import { ErrorInterceptor } from './auth/interceptors/error.interceptor';

registerLocaleData(localePt);

@NgModule({
  declarations: [
    AppComponent,
    ConfimationDialogComponent,
  ],
  imports: [
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('jwt_token'), // obtem o token
        allowedDomains: ['unitins.br'], // dominios que serao enviados o token automaticamente
        disallowedRoutes: ['localhost:8080/login'] // dominios proibidos de envio de token
      }
    }),
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule
  ],
  providers: [
    JwtHelperService,
    {provide: LOCALE_ID, useValue: 'pt'},
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }],
   
  bootstrap: [AppComponent]
})
export class AppModule { }
