import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable, catchError, of } from "rxjs";
import { Car } from "../domain/car";

@Injectable({
    providedIn: 'root'
})
export class CarService{
    private backendURL = 'http://localhost:80/xampp/lab8/server/controller/controller.php';
    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type' : 'application/json'
        })
    };

    constructor(private httpClient : HttpClient){

    }

    getCars(model : string) : Observable<Car[]>{
        return this.httpClient.get<Car[]>(`${this.backendURL}?func=selectAll&model=${model}`);
    }

    getCarById(carId: number) : Observable<Car> {
        return this.httpClient.get<Car>(`${this.backendURL}?func=selectId&carId=${carId}`);
    }

    addCar(model: string, engine: string, power: number, fuel: string, price: number, color: string, age: number, history: string){
        const body = {
            model: model,
            engine: engine,
            power: power,
            fuel: fuel,
            price: price,
            color: color,
            age: age,
            history: history
        };

        this.httpClient.post<any>(`${this.backendURL}?func=insert`, body , this.httpOptions).pipe(catchError(this.handleError<undefined>('addCar', undefined))).subscribe();
    }

    deleteCar(carId: number) : Observable<any> {
        return this.httpClient.delete<any>(`${this.backendURL}?func=delete&carId=${carId}`);
    }

    updateCar(car : Car): Observable<any>{
        return this.httpClient.put<any>(this.backendURL, car, this.httpOptions); 
    }

    private handleError<T>(operation = 'operation', result?: T){
        return (error : any) : Observable<T> => {
            console.error(error);
            return of(result as T);
        }
    }
}