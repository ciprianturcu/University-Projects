class EventHandling{
    constructor(){
        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("db-insert").addEventListener("click", this.dbInsertBtnClicked);
        document.getElementById("update-button").addEventListener("click",this.updateBtnSubmit);
        this.loadFields();
    }

    homeBtnClicked(event){
        document.location.href = "./showCars.html";
    }

    dbInsertBtnClicked(event){
        document.location.href = "./insertCar.html";
    }

    loadFields(event, thisObject){
        const urlParam = new URLSearchParams(window.location.search);
        this.carId = urlParam.get("carId");

        const parameters = new URLSearchParams({
            which: 'selectId',
            carId: this.carId
        });

        fetch("../../server/controller/controller.php?" + parameters, {method:'GET'})
        .then(response => response.json())
        .then(resultArray => {
            resultArray.forEach(function(current, index, array){
                array[index] = JSON.parse(current);
            });

            const element = resultArray[0];

            document.getElementById("model").value = element.model;
            document.getElementById("engine").value = element.engine;
            document.getElementById("power").value = element.power;
            document.getElementById("fuel").value = element.fuel;
            document.getElementById("price").value = element.price;
            document.getElementById("color").value = element.color;
            document.getElementById("age").value = element.age;
            document.getElementById("history").value = element.history;
        });
    }

    updateBtnSubmit(event){
            const urlParam = new URLSearchParams(window.location.search);
            this.carId = urlParam.get("carId");
            const model = document.getElementById("model").value;
            const engine = document.getElementById("engine").value;
            const power = document.getElementById("power").value;
            const fuel = document.getElementById("fuel").value;
            const price = document.getElementById("price").value;
            const color = document.getElementById("color").value;
            const age = document.getElementById("age").value;
            const history = document.getElementById("history").value;

            const putRequest = new XMLHttpRequest();

            putRequest.open("PUT", "../../server/controller/controller.php?"
                                +"carId="+this.carId
                                +"&model="+model
                                +"&engine="+engine
                                +"&power="+power
                                +"&fuel="+fuel
                                +"&price="+price
                                +"&color="+color
                                +"&age="+age
                                +"&history="+history
                                );
            putRequest.send();
    }

}