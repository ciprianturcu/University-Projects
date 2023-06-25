<?php

class Channel implements JsonSerializable {
    private $id;
    private $ownerid;
    private $name;
    private $description;
    private $subscribers;

    public function __construct(int $id, int $ownerid, string $name, string $description, string $subscribers) {
        $this->id = $id;
        $this->ownerid = $ownerid;
        $this->name = $name;
        $this->description = $description;
        $this->subscribers = $subscribers;
    }

    public function getId(): int {
        return $this->id;
    }

    public function getOwnerid(): int {
        return $this->ownerid;
    }

    public function getName(): string {
        return $this->name;
    }

    public function getDescription(): string {
        return $this->description;
    }

    public function getSubscribers(): string {
        return $this->subscribers;
    }

    public function jsonSerialize() {
        return [
            'id' => $this->id,
            'ownerid' => $this->ownerid,
            'name' => $this->name,
            'description' => $this->description,
            'subscribers' => $this->subscribers,
        ];
    }
}

?>