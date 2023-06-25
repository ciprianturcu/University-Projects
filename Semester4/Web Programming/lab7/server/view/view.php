<?php
require_once "../service/service.php";
class View
{
    private $service;

    public function __construct()
    {
        $this->service = new Service();
    }

    public function selectAllCars(string $model)
    {
        echo $this->service->selectAllCars($model);
    }
    public function insertCar(string $model, string $engine, int $power, string $fuel, int $price, string $color, int $age, string $history)
    {
        $this->service->insertCar($model, $engine, $power, $fuel, $price, $color, $age, $history);
    }

    public function updateCar(int $id, string $model, string $engine, int $power, string $fuel, int $price, string $color, int $age, string $history)
    {
        $this->service->updateCar($id, $model, $engine, $power, $fuel, $price, $color, $age, $history);
    }

    public function deleteCar(int $id)
    {
        $this->service->deleteCar($id);
    }

    public function selectId(int $id)
    {
        
        echo $this->service->selectId($id);
    }
}
?>