<?php

require_once "../model/person.php";
require_once "../model/channel.php";

class Repository {
    private $hostname = "localhost";
    private $username = "root";
    private $password = "";
    private $database = "subiect6";
    private $tableName = "person";
    private $tableName2 = "channel";
    private $connection = NULL;


    public function __construct() {
        $this->connect();
    }

    public function __destruct() {
        $this->connection->close();
    }

    private function connect() {
        $this->connection = new mysqli($this->hostname, $this->username, $this->password, $this->database);
        
        // check connection
        if ($this->connection->connect_error) {
            die("Connection failed: " . $this->connection->connect_error);
        }
    }

    public function getOwnerid(string $name): int {
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE name='" . $name . "'";
        $ownerid = 0;

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return 0;
        }

        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $ownerid = $row["id"];
            }
        }
        return $ownerid;

    }

    public function getChannels() {
        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "`";

        $answerArray = array();

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            echo "abc";
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $channel = new Channel((int) $row["id"], (int) $row["ownerid"], $row["name"], $row["description"], $row["subscribers"]);
                array_push($answerArray, $channel);
            }
        }

        return json_encode($answerArray);
    }

    public function getChannelsFiltered(string $ownername) {
        $ownerid = $this->getOwnerid($ownername);

        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "` WHERE `ownerid`=" . $ownerid;

        $answerArray = array();

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $channel = new Channel((int) $row["id"], (int) $row["ownerid"], $row["name"], $row["description"], $row["subscribers"]);
                array_push($answerArray, $channel);
            }
        }

        return json_encode($answerArray);
    }

    public function getChannelsSubscribed(string $name) {
        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "`";

        $answerArray = array();

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $subscribers = explode(",", $row["subscribers"]);

                foreach ($subscribers as $crt) {
                    $s = explode(":", $crt);
                    if ($s[0] == $name) {
                        $channel = new Channel((int) $row["id"], (int) $row["ownerid"], $row["name"], $row["description"], $row["subscribers"]);
                        array_push($answerArray, $channel);
                    }
                }
            }
        }

        return json_encode($answerArray);
    }

    public function subscribe(string $channel, string $user) {
        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "` WHERE `name`='" . $channel . "'";
        $answerArray = array();
        $updateDate=false;

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            $row = $result->fetch_assoc();
            $subscribers = explode(",", $row["subscribers"]);
            $newSub = "";

            foreach ($subscribers as $crt) {
                $s = explode(":", $crt);
                if ($s[0] == $user) {
                    $updateDate = true;
                    $newSub = $newSub . $s[0] . ":" . date("d/m/Y") . ",";
                }
                else {
                    $newSub = $newSub . $crt . ",";
                }
            }
        }

        if ($updateDate == false) {
            $newSub = $newSub . $user . ":" . date("d/m/Y");
        }

        $sqlQuery2 = "UPDATE `" . $this->tableName2 . "` SET `subscribers`='" . $newSub . "' WHERE `name`='" . $channel . "'";
        $this->connection->query($sqlQuery2);

    }

}


?>