import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Car } from 'src/app/domain/car';
import { CarService } from 'src/app/services/car.service';


@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  form!: FormGroup;

  constructor(private carService: CarService, private route: ActivatedRoute, private formBuilder: FormBuilder) { }

  carId: number = -1;

  model: string = "";
  engine: string = "";
  power: number = 0;
  fuel: string = "";
  price: number= 0;
  color:string="";
  age:number=0;
  history:string="";




  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam !== null) {
      this.carId = +idParam;
      console.log(this.carId);

      this.form = new FormGroup({
        model: new FormControl(''),
        engine: new FormControl(''),
        power: new FormControl(0),
        fuel: new FormControl(''),
        price: new FormControl(0),
        color: new FormControl(''),
        age: new FormControl(0),
        history: new FormControl(''),
      });

      this.carService.getCarById(this.carId).subscribe((carArry: any) => {
        console.log(carArry[0]);
        let car = carArry[0];
        this.form.setValue({
          model: car.model,
          engine: car.engine,
          power: car.power,
          fuel: car.fuel,
          price: car.price,
          color: car.color,
          age: car.age,
          history: car.history
        });
      });
    }
  }

  onSubmitUpdateButton(): void {
    if (this.carId >= 0) {

      const car = {
        id: this.carId,
        model: this.model,
        engine: this.engine,
        power: this.power,
        fuel: this.fuel,
        price: this.price,
        color: this.color,
        age: this.age,
        history: this.history
      }

      this.carService.updateCar(car).subscribe(() => {
        console.log(car);
        window.alert("Car was succesfully updated!");
        console.log('alert');
      });
    }
  }
}
