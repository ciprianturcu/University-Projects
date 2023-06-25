<?php
require_once "../repository/repository.php";
require_once "../model/car.php";

class Service
{
    private $repository;

    public function __construct()
    {
        $this->repository = new Repository();
    }
    public function selectAllCars(string $model)
    {
        return $this->repository->selectAllCars($model);
    }

    public function selectId(int $id)
    {
        return $this->repository->selectId($id);
    }

    public function insertCar(string $model, string $engine, int $power, string $fuel, int $price, string $color, int $age, string $history)
    {
        $car = new Car(0, $model, $engine, $power, $fuel, $price, $color, $age, $history);
        return $this->repository->insertCar($car);
    }
    public function updateCar(int $id, string $model, string $engine, int $power, string $fuel, int $price, string $color, int $age, string $history)
    {
        $car = new Car(0, $model, $engine, $power, $fuel, $price, $color, $age, $history);
        $this->repository->updateCar($id, $car);
    }

    public function deleteCar(int $id)
    {
        $this->repository->deleteCar($id);
    }

    
}
?>