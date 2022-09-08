import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Car } from './car';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  [x: string]: any;

private apiServerUrl = environment.apiBaseUrl;;

constructor(private http: HttpClient) { }
 
   public getCars(): Promise<Car[]>{
     return this.http.get<Car[]>(`${this.apiServerUrl}/car/all`).toPromise();
   }
   public addCar(car:Car): Observable<Car>{
    return this.http.post<Car>(`${this.apiServerUrl}/car/add`,car);
  }
   public queryCar(car:Car): Promise<Car>{
     console.log(car)
    return this.http.post<Car>(`${this.apiServerUrl}/car/query`,car).toPromise();
  }
  public queryClassify(car:Car): Promise<Car>{
   return this.http.post<Car>(`${this.apiServerUrl}/car/classify`,car).toPromise();
 }
 public relation(car:Car): Promise<Car>{
  return this.http.post<Car>(`${this.apiServerUrl}/car/relation`,car).toPromise();
}
  public updateCar( car: Car): Observable<Car>{
    return this.http.put<Car>(`${this.apiServerUrl}/car/update`,car);
  }
  public deleteCar(carId: number):
    Observable<void>{
      return this.http.delete<void>(`${this.apiServerUrl}/car/delete/${carId}`);
  }
  public predict(car:Car): Promise<any>{
    return this.http.post<Car>(`${this.apiServerUrl}/car/predict`,car).toPromise();
  }
}
