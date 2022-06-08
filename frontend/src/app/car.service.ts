import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Car } from './car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

//enviroment.ts
private apiServerUrl = environment.apiBaseUrl;;

constructor(private http: HttpClient) { }
 
   public getCars(): Observable<Car[]>{
     return this.http.get<Car[]>(`${this.apiServerUrl}/car/all`);
   }
   public addCar(car:Car): Observable<Car>{
    return this.http.post<Car>(`${this.apiServerUrl}/car/add`,car);
  }
   public queryCar(car:Car): Observable<Car>{
     console.log(car)
    return this.http.post<Car>(`${this.apiServerUrl}/car/query`,car);
  }
  updateCar( car: Car): Observable<Car>{
    return this.http.put<Car>(`${this.apiServerUrl}/car/update`,car);
  }
  deleteCar(carId: number):
    Observable<void>{
      return this.http.delete<void>(`${this.apiServerUrl}/car/delete/${carId}`);
  }
  // putData(selectedMaker: string, selectedModel: string, selectedHand: string, selectedYear: string,selectedMonth: string ,  selectedDistance: string):
  //   Observable<any>{
  //     return this.http.request<String>(`${this.apiServerUrl}/car/request/`,selectedMaker,selectedModel,selectedHand,selectedYear,selectedMonth,selectedDistance);
  // }
  

}
