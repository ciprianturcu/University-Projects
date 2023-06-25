<?php
require_once "../model/car.php";

class Repository
{
    private $hostname = "localhost";
    private $username = "root";
    private $password = "";
    private $database = "carshop";
    private $tableName = "car";
    private $connection = NULL;

    public function __construct()
    {
        $this->connect();
    }

    public function __destruct()
    {
        $this->connection->close();
    }

    private function connect()
    {

        //create connection
        $this->connection = new mysqli($this->hostname, $this->username, $this->password, $this->database);

        //check connection
        if ($this->connection->connect_error) {
            die("Connection error:" . $this->connection->connect_error);
        }
    }

    public function selectAllCars(string $model)
    {
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE model LIKE '%" . $model . "%'";

        $result = $this->connection->query($sqlQuery);
        if (!$result)
            return;
        $answerArray = array();

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $car = new Car((int) $row["ID"], $row["Model"], $row["Engine"], (int) $row["Power"], $row["Fuel"], (int) $row["Price"], $row["Color"], (int) $row["Age"], $row["History"]);
                array_push($answerArray, $car);
            }
        }

        return json_encode($answerArray);
    }

    public function selectId(int $id)
    {

        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE `ID` = '" . (string) $id . "'";

        $result = $this->connection->query($sqlQuery);

        //echo $result;
        if (!$result)
            return;
        $answerArray = array();

        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $car = new Car((int) $row["ID"], $row["Model"], $row["Engine"], (int) $row["Power"], $row["Fuel"], (int) $row["Price"], $row["Color"], (int) $row["Age"], $row["History"]);
            array_push($answerArray, $car);
        }
        return json_encode($answerArray);

    }

    public function insertCar(Car $car): bool
    {
        $sqlQuery = "INSERT INTO `" . $this->tableName . "` (`Model`, `Engine`, `Power`, `Fuel`, `Price`, `Color`, `Age`, `History`) VALUES (?,?,?,?,?,?,?,?)";

        $statement = $this->connection->prepare($sqlQuery);
        $carModel = $car->getModel();
        $carEngine = $car->getEngine();
        $carPower = $car->getPower();
        $carFuel = $car->getFuel();
        $carPrice = $car->getPrice();
        $carColor = $car->getColor();
        $carAge = $car->getAge();
        $carHistory = $car->getHistory();

        $statement->bind_param("ssisisis", $carModel, $carEngine, $carPower, $carFuel, $carPrice, $carColor, $carAge, $carHistory);
        $statement->execute();

        return true;
    }

    public function updateCar(int $carId, Car $newCar): bool
    {
        $sqlQuery = "UPDATE `" . $this->tableName
            . "` SET `Model` = '" . $newCar->getModel()
            . "', `Engine` = '" . $newCar->getEngine()
            . "', `Power` = '" . (string) $newCar->getPower()
            . "', `Fuel` = '" . $newCar->getFuel()
            . "', `Price` = '" . (string) $newCar->getPrice()
            . "', `Color` = '" . $newCar->getColor()
            . "', `Age` = '" . (string) $newCar->getAge()
            . "', `History` ='" . $newCar->getHistory()
            . "' WHERE `ID` = '" . (string) $carId . "'";

        if ($this->connection->query($sqlQuery) === TRUE){
            return true;
        }
            
        return false;
    }

    public function deleteCar(int $carID): bool
    {
        $sqlQuery = "DELETE FROM `" . $this->tableName . "` WHERE `ID` = '" . $carID . "'";

        if ($this->connection->query($sqlQuery) === TRUE)
            return true;
        return false;
    }

    public function checkCarID(int $id): string
    {
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE `ID` = " . (string) $id;
        $result = $this->connection->query($sqlQuery);

        if ($result->num_rows > 0)
            return "valid";
        return "invalid";
    }
}
?>