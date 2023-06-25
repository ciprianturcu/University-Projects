class EventHandling {
    constructor() {
        const thisObject = this;

        document.getElementById("login-btn").addEventListener("click", function() {
            thisObject.login(thisObject);
        });
    }

    login(thisObject) {
        const username = document.getElementById("user-name").value;

        const getRequest = new XMLHttpRequest();
        getRequest.open("GET", "http://localhost/subiectnr6/server/controller/controller.php?func=login&user=" + username);
        getRequest.send();

        getRequest.onload = function() {
            document.location.href = "./main.html";
        }
    }

    
}