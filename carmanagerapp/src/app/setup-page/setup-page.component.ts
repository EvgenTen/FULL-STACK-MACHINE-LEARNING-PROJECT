import { Component,  OnInit } from '@angular/core';
import {AppComponent} from '../app.component'
import { HttpErrorResponse } from '@angular/common/http';
import { CarService } from '../car.service'; 


@Component({
  selector: 'app-setup-page',
  templateUrl: './setup-page.component.html',
  styleUrls: ['./setup-page.component.css']
})
export class SetupPageComponent implements OnInit {
  [x: string]: any;
  min !: number ;
  max !: number ;
  averageNum !:number;
  flag: boolean = false;
  finalQuery: String | undefined ="";
  dataPointsBlue:any = [];
  dataPointsRed:any = [];
  dataPointsYellow:any = [];
  flagVarBlue:boolean= true;
  flagVarRed:boolean= true;
  flagVarYellow:boolean= true;
  
  constructor(private carService : CarService, private appComponent : AppComponent) { };
 



  //traffic light: low or best price - green, high price - red
   public  setColor():void{
    const circles = document.getElementsByClassName("circle");
    this.finalQuery = this.appComponent.finalQuery;
   if(this.appComponent.finalQuery === "classified as -1 (very good car)"){
    circles[0].innerHTML = this.appComponent.selectedPrice + " сlassified as good price";
    circles[0].classList.add("green");
   
    
   }
   else if(this.appComponent.finalQuery === "classification as 1 (not good car)") {
    circles[0].innerHTML = this.appComponent.selectedPrice + " сlassified as high price";
    circles[0].classList.add("red");
   
   }
   
   
   }
  
  
   
   val1= Math.max(...this.appComponent.responseQueryLow)
   val2= Math.min(...this.appComponent.responseQueryHigh)
    average= (this.val1+this.val2)/2;
    averageAVG = (arr: any[]) => arr.reduce( ( p, c ) => p + c, 0 ) / arr.length;
    
    result = this.averageAVG(this.appComponent.responseQuery);
   //Car price distribution chart
   chartOptions = {
    
    title:{
      text: "Car price distribution chart"
    },
    
    animationEnabled: true,
    axisY :{
      
      title: "ils",
      valueFormatString: "0",
      minimum:  Math.min(this.average,Math.min(...this.appComponent.responseQuery),parseInt(this.appComponent.selectedPrice))-1000,
      maximum: Math.max(Math.max(...this.appComponent.responseQuery),parseInt(this.appComponent.selectedPrice))+500,
      interval: 2000,
      
      stripLines: [{
        
        value: this.appComponent.selectedPrice,
        label: "My Price"
        
      }]
    },
    toolTip: {
      shared: "true"
    },
    legend:{
      cursor:"pointer"
     
    },
    data: [
      {
type: "spline", 
showInLegend: true,
name: "Price",
dataPoints: [
  

  { label: "Min AVG",y: this.min = Math.min(...this.appComponent.responseQuery) },
  { label: "AVG Support Vector", y: this.averageNum = this.result},
  { label: "Max AVG",y: this.max = Math.max(...this.appComponent.responseQuery) },
  
  
]
}]
 
  }	
  //third chart-> relation between km/price
  //blue data    
  public valueFunBlue():any{
        this.min=0;
        this.averageNum = 0;
        this.max=0;
    for( var i = 0;i< this.appComponent.finalRelation.length; i++ ) {
    if(this.appComponent.finalRelation[i][1][0] === -1){
    this.dataPointsBlue.push({ x : (this.appComponent.finalRelation[i][0][0]*10000), y : (this.appComponent.finalRelation[i][0][1]*10000) });
    }
  }
    return this.dataPointsBlue;
  }  
  //third chart-> relation between km/price
  //red data   
  public valueFunRed():any{
 
    for( var i = 0;i< this.appComponent.finalRelation.length; i++ ) {
      if(this.appComponent.finalRelation[i][1][0] === 1){
    this.dataPointsRed.push({ x : (this.appComponent.finalRelation[i][0][0]*10000), y : (this.appComponent.finalRelation[i][0][1]*10000) });
    }}
    
    return this.dataPointsRed;
  }
  //third chart-> relation between km/price
  //yellow data     
  public valueFunYellow():any{
 
    for( var i=0,j = 0;i< this.appComponent.responseQuery.length,j<this.appComponent.responseQueryDistance.length; i++,j++ ) {
      for( var l = 0;l< this.appComponent.finalRelation.length; l++ ) {
        if((this.appComponent.finalRelation[l][0][0]*10000) === this.appComponent.responseQueryDistance[j] && (this.appComponent.finalRelation[l][0][1]*10000)
          === this.appComponent.responseQuery[i] ){
        this.dataPointsYellow.push({ x : (this.appComponent.finalRelation[i][0][0]*10000), y : (this.appComponent.finalRelation[i][0][1]*10000) });
        }
   
    
    }}
    
    return this.dataPointsYellow;
  }   
  //Relation between km/price
       chart =  {
      animationEnabled: true,
      
      title:{
        text: "Relation between km/price",
        
      },
      axisX: {
        minimum:0,
        maximum: 110000,
        title:"km"
      },
      axisY:{
        title: "ils",
        minimum:100000,
        maximum: 150000,
        includeZero: true
      },
      data: [{
        type: "scatter",
        toolTipContent: "<span style=\"color:#4F81BC \"><b>{name}</b></span><br/><b> km:</b> {x} <br/><b> ils:</b></span> {y} ",
        name: "Low price",
        showInLegend: true,
        dataPoints: this.dataPointsBlue
      },
      {
        type: "scatter",
        toolTipContent: "<span style=\"color:#C0504E \"><b>{name}</b></span><br/><b> km:</b> {x} <br/><b> ils:</b></span> {y} ",
        name: "High price",
        showInLegend: true, 
        dataPoints: this.dataPointsRed
      },
      //support vector
      // {
      //   type: "scatter",
      //   toolTipContent: "<span style=\"color:#C0555E \"><b>{name}</b></span><br/><b> km:</b> {x} <br/><b> ils:</b></span> {y} ",
      //   name: "Support Vector",
      //   showInLegend: true, 
      //    dataPoints: this.dataPointsYellow
      // }
    ]
    }
  
  
  ngOnInit(): void {
    this.getPredict();
  }
   
  ngDoCheck(): void {
   
    if(this.appComponent.finalRelation !== undefined && this.flagVarBlue === true) {
      this.valueFunBlue();
      this.flagVarBlue=false;
    }
    //start-traffic light
    this.setColor();
    if(this.appComponent.finalRelation !== undefined && this.flagVarRed === true) {
      this.valueFunRed();
      this.flagVarRed=false;
    }
    if(this.appComponent.finalRelation !== undefined && this.flagVarYellow === true) {
      this.valueFunYellow();
      this.flagVarYellow=false;
    }
    
    
    }
  ngAfterViewInit():void{
    
  } 


  finalPredict =  new Array<any>();
  //prediction starting point
  public start(carNew: any):any{
    this.carService.predict(carNew).then(
      (relAns: any) => {
      
                this.finalPredict = new Array<any>(...relAns);
                console.log("finalPredict=",this.finalPredict)
                
          return this.finalPredict;
            },
      
      (error: HttpErrorResponse) =>{
        alert(error.message);
      
      }
    );

   }  
//get predict       
public getPredict():void{
  let btnSearch = document.querySelector('#predict');
    btnSearch?.addEventListener('click', () =>
    { 
    console.log("carNew",this.appComponent.carNew)
    this.start(this.appComponent.carNew);
    this.getYearPredict();
    })
} 
yearPredict = new Array<any>();
//get year to prediction
public getYearPredict():void{
  let ver = Number(this.appComponent.carNew?.year);
  for (let i = 0; i < 10; i++) {
  if(i==5){ver=Number(this.appComponent.carNew?.year);}
  ver = ver+1;
  this.yearPredict.push(ver);
  
  console.log("year"+this.appComponent.carNew?.year)}
}

}

