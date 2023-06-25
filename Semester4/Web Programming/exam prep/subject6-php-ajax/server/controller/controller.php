<?php

require_once "../repository/repository.php";

session_start();

class Controller {
    private $repo;

    public function __construct() {
        $this->repo = new Repository();
    }

    public function serve() {

        if(isset($_GET["func"]) && $_GET["func"] === "login") {
            $this->serveLogin();
        }

        if(isset($_GET["func"]) && $_GET["func"] === "getChannels"){
            $this->serveGetChannelsFiltered();
        }

        if (isset($_GET["func"]) && $_GET["func"] === "getChannelSubs") {
            $this->serveGetChannelsSubscribed();
        }

        if (isset($_GET["func"]) && $_GET["func"] === "subscribe") {
            $this->serveSubscribe();
        }
    }

    private function serveSubscribe() {
        if (!isset($_GET["channel"])){
            return;
        }

        echo $this->repo->subscribe($_GET["channel"], $_SESSION["user"]);
    }

    private function serveLogin() {
        if (!isset($_GET["user"])) {
            return;
        }
    
        $_SESSION["user"] = $_GET["user"]; 
    }

    private function serveGetChannelsSubscribed() {
        echo $this->repo->getChannelsSubscribed($_SESSION["user"]);
    }

    private function getOwner() {
        if (!isset($_GET["name"])){
            return;
        }

        echo $this->repo->getOwnerid($_GET["name"]);
    }

    private function serveGetChannels() {

        echo $this->repo->getChannels();
    }

    private function serveGetChannelsFiltered() {
        if (!isset($_GET["name"])){
            return;
        }

        echo $this->repo->getChannelsFiltered($_GET["name"]);
    }
}

$controller = new Controller();
$controller->serve();

?>