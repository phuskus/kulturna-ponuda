import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
//import { MainModule } from './modules/main/main.module';
// import { AdminModule } from './modules/admin/admin.module';
//import { LoginModule } from './modules/login/login.module';
// import { RegisterModule } from './modules/register/register.module';
import { TokenInterceptorService } from './services/auth/interceptors/token-interceptor.service';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    NgxPaginationModule,
    BrowserAnimationsModule,

    HttpClientModule
    // MainModule,
    // AdminModule,
    // LoginModule,
    // RegisterModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
