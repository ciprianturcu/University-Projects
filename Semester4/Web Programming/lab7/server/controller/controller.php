<?php
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
            if ($_GET["which"] === "c") {
                $this->serveInsert();
                return;
            }
        }

        if ($_SERVER['REQUEST_METHOD'] === "DELETE") {
            if ($_GET["which"] === "delC") {
                $this->serveDelete();
                return;
            }
        }

        if ($_SERVER['REQUEST_METHOD'] === "GET") {
            if ($_GET["which"] === 'selectId') {
                $carId = $_GET["carId"];
                $this->serveSelectId($carId);
                return;
            }
            if ($_GET["which"] === 'select') {
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
        if (!isset($_GET["model"]) || !isset($_GET["engine"]) || !isset($_GET["power"]) || !isset($_GET["fuel"]) || !isset($_GET["price"]) || !isset($_GET["color"]) || !isset($_GET["age"]) || !isset($_GET["history"])) {
            return;
        }
        $this->view->insertCar($_GET["model"], $_GET["engine"], $_GET["power"], $_GET["fuel"], $_GET["price"], $_GET["color"], $_GET["age"], $_GET["history"]);
    }

    private function serveUpdate()
    {
        if (!isset($_GET["carId"]) || !isset($_GET["model"]) || !isset($_GET["engine"]) || !isset($_GET["power"]) || !isset($_GET["fuel"]) || !isset($_GET["price"]) || !isset($_GET["color"]) || !isset($_GET["age"]) || !isset($_GET["history"])) {
            return;
        }
        $this->view->updateCar($_GET["carId"],$_GET["model"], $_GET["engine"], $_GET["power"], $_GET["fuel"], $_GET["price"], $_GET["color"], $_GET["age"], $_GET["history"]);
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