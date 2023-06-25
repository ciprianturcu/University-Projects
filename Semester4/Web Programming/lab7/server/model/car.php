<?php
class Car implements JsonSerializable{
    private $id;
    private $model;
    private $engine;
    private $power;
    private $fuel;
    private $price;
    private $color;
    private $age;
    private $history;

    public function __construct(int $id, 
                                string $model, 
                                string $engine, 
                                int $power,
                                string $fuel,
                                int $price,
                                string $color,
                                int $age,
                                string $history
                                )
    {
        $this->id = $id;
        $this->model = $model;
        $this->engine = $engine;
        $this->power = $power;
        $this->fuel = $fuel;
        $this->price = $price;
        $this->color = $color;
        $this->age = $age;
        $this->history = $history;
    }

    public function JsonSerialize(){
        return [
            'id' => $this->id,
            'model' => $this->model,
            'engine' => $this->engine,
            'power' => $this->power,
            'fuel' => $this->fuel,
            'price' => $this->price,
            'color' => $this->color,
            'age' => $this->age,
            'history' => $this->history
        ];
    }


    public function getModel(){return $this->model;}
    public function getEngine(){return $this->engine;}
    public function getPower(){return $this->power;}
    public function getFuel(){return $this->fuel;}
    public function getPrice(){return $this->price;}
    public function getColor(){return $this->color;}
    public function getAge(){return $this->age;}
    public function getHistory(){return $this->history;}

}
?>