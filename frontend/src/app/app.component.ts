import { HttpErrorResponse } from '@angular/common/http';
import { Component,  OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Car } from './car';
import { CarService } from './car.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public cars: Car[] = [];
  selectedMaker: string = '';
  public uniqueMaker: any[] =[];
  selectedModel: string = '';
  public uniqueModel: any[] =[];
  selectedHand: string = '';
  public uniqueHand: any[] =[];
  selectedYear: string = '';
  public uniqueYear: any[] =[];
  selectedMonth: string = '';
  public uniqueMonth: any[] =[];
  selectedDistance: string = '';
  public uniqueDistance: any[] =[];
  public carsFullSearch : any[] =[];
  response: string[]=[];
  
  constructor(private carService : CarService) { }
  

  ngOnInit(): void {
    this.getCars();
    this.getMakers();
    this.onSearch();
  }

  public getMakers(): void {
    let carsMaker: Car[] =[];
    let carsMakerString: any[] =[];
    this.carService.getCars().subscribe(
      (response: Car[]) => {
        carsMaker = response;
        
       carsMakerString.push(carsMaker.map(x => x.manufacturer))
        this.uniqueMaker = [...new Set(carsMakerString[0])];
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  public getModel(): void {
    let carsModel: any[] =[];
    let carsModelString: any[] =[];
    this.carService.getCars().subscribe(
      response => {
        carsModel = response.filter(response => response.manufacturer === this.selectedMaker);
        carsModelString.push(carsModel.map(x => x.model))
        this.uniqueModel = [...new Set(carsModelString[0])];
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  public getHand(): void {
    let carsHand: any[] =[];
    let carsHandString: any[] =[];
    this.carService.getCars().subscribe(
      response => {
        carsHand = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel);  
        carsHandString.push(carsHand.map(x => x.hand))

        this.uniqueHand = [...new Set(carsHandString[0])];
        
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  public getYear(): void {
    let carsYear: any[] =[];
    let carsYearString: any[] =[];
    this.carService.getCars().subscribe(
      response => {
        carsYear = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel && String(response.hand) === this.selectedHand);
        carsYearString.push(carsYear.map(x => x.year));
        this.uniqueYear = [...new Set(carsYearString[0])];
        
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  public getMonth(): void {
    let carsMonth: any[] =[];
    let carsMonthString: any[] =[];
    this.carService.getCars().subscribe(
      response => {
        carsMonth = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel && String(response.hand) === this.selectedHand && 
        String(response.year) === this.selectedYear);
        carsMonthString.push(carsMonth.map(x => x.month))
        this.uniqueMonth = [...new Set(carsMonthString[0])];
        
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  public getCars(): void{
    this.carService.getCars().subscribe(
      (response: Car[]) => {
        this.cars = response; 
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    }
    public  onAddKm(addForm: NgForm): void {
    let carsDistance: any[] =[];
    console.log(addForm.value.distance)
    this.selectedDistance = addForm.value.distance;
    this.carService.getCars().subscribe(
      response => {
        carsDistance = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel && String(response.hand) === this.selectedHand && 
        String(response.year) === this.selectedYear  && String(response.month) === this.selectedMonth &&
        String(response.distance) === this.selectedDistance);
        this.carsFullSearch=carsDistance;  
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  
    //event handler for the select element's change event
    selectChangeHandlerMaker (event: any) {
      //update the ui
      this.selectedMaker = event.target.value;
      this.getModel();
      console.log(this.selectedMaker);
    } 
    selectChangeHandlerModel (event: any) {
      //update the ui
      this.selectedModel = event.target.value;
      this.getHand();
      console.log(this.selectedModel);
    }  
    selectChangeHandlerHand (event: any) {
      //update the ui
      this.selectedHand = event.target.value;
      this.getYear();
      console.log(this.selectedHand);
    } 
    selectChangeHandlerYear (event: any) {
      //update the ui
      this.selectedYear = event.target.value;
      this.getMonth();
      console.log(this.selectedYear);
    } 
    selectChangeHandlerMonth (event: any) {
      //update the ui
      this.selectedMonth = event.target.value;
      console.log(this.selectedMonth);
    } 
    
    public onSearch(): void {
      let btnSearch = document.querySelector('#search');
      
      btnSearch?.addEventListener('click', () =>
      { if (this.selectedMaker !== "" && this.selectedYear !== "" && this.selectedMonth!== "" &&
      this.selectedHand!== "" && this.selectedDistance!== "" && this.selectedModel!== ""){ 
      
      let carNew :Car = {
        id: 0,
        manufacturer: this.selectedMaker,
        model: this.selectedModel,
        year: parseInt(this.selectedYear),
        month: parseInt(this.selectedMonth),
        distance: parseInt(this.selectedDistance),
        hand:  parseInt(this.selectedHand),
        imageUrl: '',
        carCode: '',
        price: 0,
        date: new Date('01 Jan 1970 00:00:00 GMT')
      }
        alert(carNew.distance)
        this.carService.queryCar(carNew).subscribe(
          (response: Car) => {
            console.log(response);
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );}})
     
     }
     public onOpenModal(car: Car |null , mode:string): void {
      const container = document.getElementById('navbarColor02');
      const button = document.createElement('button');
      button.type = 'button';
      button.style.display = 'none';
      button.setAttribute('data-toggle','modal');
      if(mode == 'add'){
       button.setAttribute('data-target','#addCarModal');
      }
      container?.appendChild(button);
      button.click();
     }
     public  onAddCar(addForm: NgForm): void {
      console.log(addForm.value)
    document.getElementById('add-car-form')?.click();// ? my- button close
    this.carService.addCar(addForm.value).subscribe(
      (response: Car) => {
        console.log(response)
        this.getCars(); 
        addForm.reset();   
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
        addForm.reset();
      }
    );
    } 
    }


