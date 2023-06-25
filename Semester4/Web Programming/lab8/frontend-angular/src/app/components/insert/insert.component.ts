import { Component, OnInit } from "@angular/core";
import { CarService } from "src/app/services/car.service";

@Component({
    selector: 'app-insert',
    templateUrl: './insert.component.html',
    styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit {
    private carModel: string = "";
    private carEngine: string = "";
    private carPower: number = 0;
    private carFuel: string="";
    private carPrice: number=0;
    private carColor: string="";
    private carAge: number=0;
    private carHistory: string="";

    constructor(private carService: CarService) {

    }

    set model(model: string) {
        this.carModel = model;
    }

    set engine(engine: string) {
        this.carEngine = engine;
    }

    set power(power: number) {
        this.carPower = power;
    }

    set fuel(fuel: string) {
        this.carFuel = fuel;
    }
    set price(price: number) {
        this.carPrice = price;
    }
    set color(color: string) {
        this.carColor = color;
    }
    set age(age: number) {
        this.carAge = age;
    }
    set history(history: string) {
        this.carHistory = history;
    }


    ngOnInit(): void {
        
    }

    onSubmitInsertButton(): void {
        this.carService.addCar(this.carModel, this.carEngine, this.carPower, this.carFuel, this.carPrice, this.carColor, this.carAge, this.carHistory);
        window.alert("Car was inserted successfully!");
    }
}