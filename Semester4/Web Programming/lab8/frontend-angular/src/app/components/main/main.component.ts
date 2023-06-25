import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Car } from "src/app/domain/car";
import { CarService } from "src/app/services/car.service";

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})

export class MainComponent implements OnInit{

    cars : Car[] =[];

    indexCar: number = -1;
    modelInputValue: string ="";

    constructor(private carService: CarService, private router: Router){
        
    }

    changeModelInput(modelInput :  HTMLInputElement): void{
        this.modelInputValue = modelInput.value;
    }

    ngOnInit(): void {
        this.indexCar=-1;
        this.getCars();
    } 

    getCars() : void {
        console.log(this.modelInputValue);
        this.carService.getCars(this.modelInputValue).subscribe((cars) =>{
            this.cars = cars;
        });
    }

    redirectToUpdateComponent(): void{
        if(this.indexCar >= 0){
            this.router.navigate(['/update', this.indexCar])
        }
        else{
            window.alert("Please select a car first!");
            this.getCars();
        }
    }

    deleteSelectedCar(): void{
        if(this.indexCar >= 0)
        {
            this.carService.deleteCar(this.indexCar).subscribe(() => {
                window.alert("Car was succesfully deleted!");
                this.indexCar=-1;
                this.getCars();
              });
        }
        else{
            window.alert("Please select a car first!");
            this.indexCar=-1;
            this.getCars();
        }
        
    }

    changeSelected(car: Car): void {
        this.indexCar = car.id;
    }
}