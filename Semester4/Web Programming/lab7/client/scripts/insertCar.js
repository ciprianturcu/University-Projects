class EventHandling{
    constructor(){
        const thisObject = this;

        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("submit-update").addEventListener("click", this.submitBtnClicked);
    }

    submitBtnClicked(event){
        const model = document.getElementById("model").value;
        const engine = document.getElementById("engine").value;
        const power = document.getElementById("power").value;
        const fuel = document.getElementById("fuel").value;
        const price = document.getElementById("price").value;
        const color = document.getElementById("color").value;
        const age = document.getElementById("age").value;
        const history = document.getElementById("history").value;

        const postRequest = new XMLHttpRequest();

        postRequest.open("POST", "../../server/controller/controller.php?which=c"
                            +"&model="+model
                            +"&engine="+engine
                            +"&power="+power
                            +"&fuel="+fuel
                            +"&price="+price
                            +"&color="+color
                            +"&age="+age
                            +"&history="+history
                            );
        postRequest.send();
        window.alert("Car was added successfully");
    }

    homeBtnClicked(event){
        document.location.href = "./showCars.html";
    }
}