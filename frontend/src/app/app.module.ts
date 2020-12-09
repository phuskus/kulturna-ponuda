import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MainModule } from './modules/main/main.module';
// import { AdminModule } from './modules/admin/admin.module';
// import { LoginModule } from './modules/login/login.module';
// import { RegisterModule } from './modules/register/register.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,

    // MainModule,
    // AdminModule,
    // LoginModule,
    // RegisterModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
