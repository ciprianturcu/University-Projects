<?php

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
require_once "../view/view.php";

class Controller
{
    private $view;

    public function __construct()
    {
        $this->view = new View();
    }

    public function serve()
    {
        if (!isset($_SERVER['REQUEST_METHOD'])) {
            return;
        }

        if ($_SERVER['REQUEST_METHOD'] === "POST") {
            if ($_GET["func"] === "insert") {
                $this->serveInsert();
                return;
            }
        }

        if ($_SERVER['REQUEST_METHOD'] === "DELETE") {
            if ($_GET["func"] === "delete") {
                $this->serveDelete();
                return;
            }
        }

        if ($_SERVER['REQUEST_METHOD'] === "GET") {
            if ($_GET["func"] === 'selectId') {
                $carId = $_GET["carId"];
                $this->serveSelectId($carId);
                return;
            }
            if ($_GET["func"] === 'selectAll') {
                $model = $_GET["model"] ?? '';
                $this->serveSelect($model);
                return;
            }
        }

        if ($_SERVER['REQUEST_METHOD'] === "PUT") {
            $this->serveUpdate();
            return;
        }
    }

    private function serveSelect(string $model)
    {
        $this->view->selectAllCars($model);
    }

    private function serveSelectId(int $carId){
        $this->view->selectId($carId);
    }

    private function serveInsert()
    {
        $json = file_get_contents("php://input");
        $data = json_decode($json, true);
        if (!isset($data["model"]) || !isset($data["engine"]) || !isset($data["power"]) || !isset($data["fuel"]) || !isset($data["price"]) || !isset($data["color"]) || !isset($data["age"]) || !isset($data["history"])) {
            return;
        }
        $this->view->insertCar($data["model"], $data["engine"], $data["power"], $data["fuel"], $data["price"], $data["color"], $data["age"], $data["history"]);
    }

    private function serveUpdate()
    {
        $json = file_get_contents("php://input");
        $data = json_decode($json, true);
        if (!isset($data["id"]) || !isset($data["model"]) || !isset($data["engine"]) || !isset($data["power"]) || !isset($data["fuel"]) || !isset($data["price"]) || !isset($data["color"]) || !isset($data["age"]) || !isset($data["history"])) {
            return;
        }
        $this->view->updateCar($data["id"],$data["model"], $data["engine"], $data["power"], $data["fuel"], $data["price"], $data["color"], $data["age"], $data["history"]);
    }

    private function serveDelete()
    {
        if (!isset($_GET["carId"])) {
            return;
        }
        $this->view->deleteCar((int) $_GET["carId"]);
    }
}

$controller = new Controller();
$controller->serve();
?>