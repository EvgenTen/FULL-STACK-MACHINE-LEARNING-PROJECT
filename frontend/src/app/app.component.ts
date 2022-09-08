import { HttpErrorResponse } from '@angular/common/http';
import { Component,  OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Car } from './car';
import { CarService } from './car.service'; 
import {Injectable} from '@angular/core';
import { LocalDate} from '@js-joda/core';
import { isNumeric } from 'rxjs/util/isNumeric';


@Injectable({providedIn: 'any'})
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  [x: string]: any;
  title(title: any) {
    throw new Error('Method not implemented.');
  }
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
  selectedPrice: string = '';
  public svKey: string = '';
  public svCars: Car[] = this.cars;
  responseQuery: number []=[];
  responseQueryLow: number []=[];
  responseQueryHigh: number []=[];
  responseQueryDistance: number []=[];
  finalQuery?: String;
  static finalRelation: any [][][] ;
 

  
  constructor(private carService : CarService, private router: Router) { }
  

  ngOnInit(): void {
    this.getCars();
    this.getMakers();
    this.onSearch();
  }
  ngDoCheck(): void {
   
  }
  // get makers
  public getMakers(): void {
    let carsMaker: Car[] =[];
    let carsMakerString: any[] =[];
    this.carService.getCars().then(
      (response: Car[]) => {
       carsMaker = response;
       
       carsMakerString.push(carsMaker.map(x => x.manufacturer))
        //unique maker : Mazda
        //this.uniqueMaker = [...new Set(carsMakerString[0])];
        this.uniqueMaker = ['Mazda'];
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  //get model
  public getModel(): void {
    let carsModel: any[] =[];
    let carsModelString: any[] =[];
    this.carService.getCars().then(
      response => {
        carsModel = response.filter(response => response.manufacturer === this.selectedMaker);
        carsModelString.push(carsModel.map(x => x.model))
        //this.uniqueModel = [...new Set(carsModelString[0])];
        //unique model: 3
        this.uniqueModel=['3']

      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  //get hand
  public getHand(): void {
    let carsHand: any[] =[];
    let carsHandString: any[] =[];
    this.carService.getCars().then(
      response => {
        carsHand = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel);  
        carsHandString.push(carsHand.map(x => x.hand))

        //this.uniqueHand = [...new Set(carsHandString[0])];
        //unique hand: 1
        this.uniqueHand = ['1']
        
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  //get year
  public getYear(): void {
    let carsYear: any[] =[];
    let carsYearString: any[] =[];
    this.carService.getCars().then(
      response => {
        carsYear = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel && String(response.hand) === this.selectedHand);
        carsYearString.push(carsYear.map(x => x.year));
        //unique year: 2021
        //this.uniqueYear = [...new Set(carsYearString[0])];
        this.uniqueYear = ['2021']
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  //get month
  public getMonth(): void {
    let carsMonth: any[] =[];
    let carsMonthString: any[] =[];
    this.carService.getCars().then(
      response => {
        carsMonth = response.filter(response => response.manufacturer ===  this.selectedMaker &&
        response.model === this.selectedModel && String(response.hand) === this.selectedHand && 
        String(response.year) === this.selectedYear);
        carsMonthString.push(carsMonth.map(x => x.month))
        //unique month: 2021
        //this.uniqueMonth = [...new Set(carsMonthString[0])];
        this.uniqueMonth=['3']
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    
  }
  // get all cars
  public getCars(): void{
    
     this.carService.getCars().then(
      (response: Car[]) => {
        this.cars = response; 
        this.svCars = this.cars;
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
    }
    
 //adds the latest km and price data
 //sends a request with all the selected data to the backend
 //checks that price and distance are entered as a number
    public onAddKm(addForm: NgForm): void {
    let carsDistance: any[] =[];
    console.log(addForm.value.distance)
    console.log(addForm.value.price)
    this.selectedDistance = addForm.value.distance;
    if (isNumeric(this.selectedDistance)){
    
   }
   else alert("Distance is no number !");
    this.selectedPrice = addForm.value?.price;
    if (isNumeric(this.selectedPrice)){
      
   }else alert("Price is no number !");
   
    this.carService.getCars().then(
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
  
    //event handler for the select element's change event -> maker
    selectChangeHandlerMaker (event: any) {
      
      this.selectedMaker = event.target.value;
      this.getModel();
      console.log(this.selectedMaker);
    } 
    //event handler for the select element's change event -> model
    selectChangeHandlerModel (event: any) {
     
      this.selectedModel = event.target.value;
      this.getHand();
      console.log(this.selectedModel);
    }  
    //event handler for the select element's change event -> hand
    selectChangeHandlerHand (event: any) {
     
      this.selectedHand = event.target.value;
      this.getYear();
      console.log(this.selectedHand);
    } 
    //event handler for the select element's change event -> year
    selectChangeHandlerYear (event: any) {
      
      this.selectedYear = event.target.value;
      this.getMonth();
      console.log(this.selectedYear);
    } 
    //event handler for the select element's change event -> month
    selectChangeHandlerMonth (event: any) {
      
      this.selectedMonth = event.target.value;
      console.log(this.selectedMonth);
    } 
    //move to another page-> http://localhost:4200/setup
    redirectTo(){
      
      if(this.router.url === '/'){
        this.router.navigate(['/setup']);
        
       }
       
    
   }
   //return to the main page in case of a new search
   public start(){
    if(isNumeric(this.selectedDistance)&&isNumeric(this.selectedPrice)){
    if(this.router.url === '/setup'){
      this.router.navigate(['/']);
    }
  }
  }
   
  //third chart-> relation between km/price
   public onRelation(carNew: Car):any{
    this.carService.relation(carNew).then(
      (relAns: any) => {
      //0: Array(2)
      //     0: (2) [1.7, 12.29]
      //       1: (2) [-1, 0]...
                this.finalRelation=relAns;
                console.log("finalRelation=",this.finalRelation)
                this.redirectTo();
          return this.finalRelation;
            },
      
      (error: HttpErrorResponse) =>{
        alert(error.message);
      
      }
    );

   }
   //first chart-> classifier 
   //second chart -> car price distribution chart
   //start function-> onRelation(carNew)
   public onQuery(carNew: Car):any{ 
    this.carService.queryCar(carNew).then(
      (ansq: any) => {
        this.onRelation(carNew);
        for(var i = 0; i < ansq.length; i++){ 
        if(ansq[i][0][0] != null){
        
         this.responseQuery.push(ansq[i][0][0].price);
         this.responseQueryDistance.push(ansq[i][0][0].distance);
         if(ansq[i][1]==-1){
         this.responseQueryLow.push(ansq[i][0][0].price);
         }
         if(ansq[i][1]==1){
          this.responseQueryHigh.push(ansq[i][0][0].price);
          }
        }
        }
      
        this.carService.queryClassify(carNew).then(
          (res: any) => {
          
           console.log("res="+res)
           for(var i = 0; i < res.length; i++){ 
           this.finalQuery = res[1];}
           
           console.log("final="+this.finalQuery)
          
           return this.finalQuery;
           
        },
         
         (error: HttpErrorResponse) =>{
           alert(error.message);
         
         }
       );
      
        return this.responseQuery;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    
    );
   }
  
   carNew :Car | undefined 
   //two functions are launched on pressing the search button->1.start() 2.onQuery(this.carNew)
   public onSearch(): void {
    
    let btnSearch = document.querySelector('#search');
    btnSearch?.addEventListener('click', () =>
    { if (isNumeric(this.selectedDistance) && isNumeric(this.selectedPrice)&&this.selectedMaker !== "" && this.selectedYear !== "" && this.selectedMonth!== "" &&
    this.selectedHand!== "" && this.selectedDistance!== "" && this.selectedModel!== "" && this.selectedPrice !== ""){ 
      this.responseQuery=[];
      this.responseQueryLow=[];
      this.responseQueryDistance=[];
      this.responseQueryHigh=[];
      this.carNew  = {
      id: 0,
      manufacturer: this.selectedMaker,
      model: this.selectedModel,
      year: parseInt(this.selectedYear),
      month: parseInt(this.selectedMonth),
      distance: parseInt(this.selectedDistance),
      hand:  parseInt(this.selectedHand),
      imageUrl: '',
      carCode: '',
      price: parseInt(this.selectedPrice),
      date: LocalDate.of(1970,1,1)
    }
    this.start();
    this.onQuery(this.carNew);
   
    
      
    }})
    
  
   
   }

     
    
    //database search
    public searchCars(key:  string  ): void {
      console.log(key);
      if (this.svKey != key) {
         this.cars = this.svCars;
      }
      let results: Car[] = [];
      let helpResults: Car[] = [];
      
      const keyArray = key.split(' ');
      console.log(keyArray);
      this.svKey = key;
      for (const car of this.svCars) {
        
         if (car.manufacturer.toLowerCase().indexOf(keyArray[0].toLowerCase()) !== -1
         || car.model.toLowerCase().indexOf(keyArray[0].toLowerCase()) !== -1
         || car.price == Number(keyArray[0])  || car.distance == Number(keyArray[0])
         || car.year == Number(keyArray[0]) || "month" == keyArray[0].toLowerCase()
         ||  "hand" == keyArray[0].toLowerCase()){
          results.push(car);
        }
      
    }
        for (let i = 1; i < keyArray.length; i++) {
          helpResults = [];
          
          for (const ans of  results) {
           if ( (ans.month === Number(keyArray[i]) && "month" === keyArray[i-1].toLowerCase())
           || (ans.hand === Number(keyArray[i]) && "hand" === keyArray[i-1].toLowerCase()) || ans.manufacturer.toLowerCase().indexOf(keyArray[i].toLowerCase()) !== -1
           || (ans.model.toLowerCase().indexOf(keyArray[i].toLowerCase()) !== -1 && "month" !== keyArray[i-1].toLowerCase() && "hand" !== keyArray[i-1].toLowerCase() )
           || (ans.price === Number(keyArray[i]) && "month" !== keyArray[i-1].toLowerCase() && "hand" !== keyArray[i-1].toLowerCase() )|| (ans.distance === Number(keyArray[i])
           && "month" !== keyArray[i-1].toLowerCase() && "hand" !== keyArray[i-1].toLowerCase())
           || (ans.year === Number(keyArray[i])&& "month" !== keyArray[i-1].toLowerCase() && "hand" !== keyArray[i-1].toLowerCase())) {
          
             helpResults.push(ans);
             console.log("month"+ans.month)
             console.log("i="+i)
             console.log(ans.month === Number(keyArray[i] && "month" === keyArray[i-1].toLowerCase()))
             results=[];
             results = helpResults;
          
        }
        
     }
     
    }
    
   
      
      if (results.length === 0 || !key) {
        this.getCars();
        
      }else 
      this.cars = results;
    }
      
  }