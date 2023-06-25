class EventHandling{
    constructor()
    {
        const thisObject = this;
        this.selectedId = -1;
       

        thisObject.loadCars(thisObject);

        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("db-insert").addEventListener("click", this.dbInsertBtnClicked);

        document.getElementById("car-update").addEventListener("click", function(){
            if(thisObject.selectedId < 0){
                window.alert("First select the car to be updated!");
                return;
            }
    
            document.location.href = './updateCar.html?carId=' + thisObject.selectedId;
        });

        document.getElementById("car-delete").addEventListener("click", function(){
            if(thisObject.selectedId < 0){
                window.alert("First select the car to be deleted!");
                return;
            }
            const carId = thisObject.selectedId
            console.log(carId);
            
            const deleteRequest = new XMLHttpRequest();
            deleteRequest.open("DELETE", "../../server/controller/controller.php?which=delC&carId="+ carId);
            deleteRequest.send();
            window.alert("Car was successfully removed from the shop! :)");
            thisObject.loadCars(thisObject);
        });


        document.getElementById("model").addEventListener("input", function(){
            thisObject.loadCars(thisObject);
        });
        
    }

    homeBtnClicked(event){
        document.location.href = "./showCars.html";
    }

    dbInsertBtnClicked(event){
        document.location.href = "./insertCar.html";
    }
    
    changeSelectedId(carId, model, engine, power, fuel, price, color, age, history){
        this.selectedId = carId;
        this.model = model;
        this.engine = engine;
        this.power = power;
        this.fuel = fuel;
        this.price = price;
        this.color = color;
        this.age = age;
        this.history = history;
    }

    loadCars(thisObject)
    {
        let model = document.getElementById("model");

        console.log(model.value);

        const parameters = new URLSearchParams({
            which: 'select',
            model: model.value
        });

        fetch('../../server/controller/controller.php?'+parameters, {method: 'GET'})
        .then(response => response.json())
        .then(resultArray => {
            resultArray.forEach(function(current, index, array){
                array[index] = JSON.parse(current);
            });

            let table =document.getElementById("cols");

            while(table.firstChild){
                table.removeChild(table.firstChild);
            }

            resultArray.forEach(element => {
                //create new row
                const newRow = document.createElement('tr');
                newRow.id = element.id;
                //create new data cells for row
                const modelCell = document.createElement('td');
                modelCell.textContent = element.model;
                newRow.appendChild(modelCell);

                const engineCell = document.createElement('td');
                engineCell.textContent = element.engine;
                newRow.appendChild(engineCell);

                const powerCell = document.createElement('td');
                powerCell.textContent = element.power;
                newRow.appendChild(powerCell);

                const fuelCell = document.createElement('td');
                fuelCell.textContent = element.fuel;
                newRow.appendChild(fuelCell);

                const priceCell = document.createElement('td');
                priceCell.textContent = element.price;
                newRow.appendChild(priceCell);

                const colorCell = document.createElement('td');
                colorCell.textContent = element.color;
                newRow.appendChild(colorCell);
                
                const ageCell = document.createElement('td');
                ageCell.textContent = element.age;
                newRow.appendChild(ageCell);

                const historyCell = document.createElement('td');
                historyCell.textContent = element.history;
                newRow.appendChild(historyCell);

                newRow.onclick = function() {
                    thisObject.changeSelectedId(element.id, element.model, element.engine, element.power, element.fuel, element.price, element.color, element.age, element.history);
                    
                    console.log(element);

                    var rows = document.querySelectorAll("#cols tr");

                    rows.forEach(function(row){
                        row.addEventListener("click", function(){
                            // Remove the "selected" class from any previously selected rows
                            document.querySelectorAll("#cols tr.selected").forEach(function(selectedRow){
                                selectedRow.classList.remove("selected");
                            });
                            // Add the "selected" class to the clicked row
                            row.classList.add("selected");
                        });
                    });
                };

                table.appendChild(newRow);

            });
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }
}