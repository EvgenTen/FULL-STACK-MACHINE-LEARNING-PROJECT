import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { CarService } from './car.service';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { SetupPageComponent } from './setup-page/setup-page.component';
import { AppRoutingModule } from './app-routing.module';
import * as CanvasJSAngularChart from '../assets/canvasjs.angular.component';
var CanvasJSChart = CanvasJSAngularChart.CanvasJSChart;
 


const routes: Routes = [
  {path:'',component : HomePageComponent },
  {path:'setup',component : SetupPageComponent }];

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    SetupPageComponent,
    CanvasJSChart
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    AppRoutingModule

  ],
  providers: [CarService,AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
