class EventHandling {
    constructor() {
        const thisObject = this;
        document.getElementById("filter-channels-btn").addEventListener("click", function() {
            thisObject.loadChannels(thisObject);
        });

        thisObject.loadSubscribedChannels(thisObject);

        document.getElementById("subscribe-btn").addEventListener("click", function() {
            thisObject.subscribe(thisObject);
        })
    }

    subscribe(thisObject) {
        const channel = document.getElementById("channel-name").value;
        if (channel == "") {
            window.alert("Please fill in the channel!");
            return;
        }

        const putRequestBody = "func=subscribe&channel=" + channel;
        const putRequest = new XMLHttpRequest();
        
        putRequest.open("PUT", "http://localhost/subiectnr6/server/controller/controller.php?func=subscribe&channel=" + channel);
        putRequest.send();

        window.alert("Successfully subscribed! :)");
        thisObject.loadSubscribedChannels(thisObject);
    }

    loadSubscribedChannels(thisObject) {
        const getRequest = new XMLHttpRequest();
        
        getRequest.open("GET", "http://localhost/subiectnr6/server/controller/controller.php?func=getChannelSubs");
        getRequest.send();

        const table = document.getElementById('cols2');

        while(table.firstChild){
            table.removeChild(table.firstChild);
        }


        getRequest.onload = function() {
            const resultArray = JSON.parse(this.responseText);



            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the name cell and add it to the row
                const nameCell = document.createElement('td');
                nameCell.textContent = element.name;
                newRow.appendChild(nameCell);

                // create the description cell and add it to the row
                const descriptionCell = document.createElement('td');
                descriptionCell.textContent = element.description;
                newRow.appendChild(descriptionCell);

                // append the row to the table
                table.appendChild(newRow);
            })
        }
    }

    loadChannels(thisObject) {
        const getRequest = new XMLHttpRequest();
        const name = document.getElementById("owner-name").value;
        
        getRequest.open("GET", "http://localhost/subiectnr6/server/controller/controller.php?func=getChannels&name=" + name);
        getRequest.send();

        const table = document.getElementById('cols');

        while(table.firstChild){
            table.removeChild(table.firstChild);
        }


        getRequest.onload = function() {
            const resultArray = JSON.parse(this.responseText);



            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the ownerid cell and add it to the row
                const idCell = document.createElement('td');
                idCell.textContent = element.id;
                newRow.appendChild(idCell);

                // create the ownerid cell and add it to the row
                const owneridCell = document.createElement('td');
                owneridCell.textContent = element.ownerid;
                newRow.appendChild(owneridCell);

                // create the name cell and add it to the row
                const nameCell = document.createElement('td');
                nameCell.textContent = element.name;
                newRow.appendChild(nameCell);

                // create the description cell and add it to the row
                const descriptionCell = document.createElement('td');
                descriptionCell.textContent = element.description;
                newRow.appendChild(descriptionCell);

                // create the id cell and add it to the row
                const subscribersCell = document.createElement('td');
                subscribersCell.textContent = element.subscribers;
                newRow.appendChild(subscribersCell);

                // append the row to the table
                table.appendChild(newRow);
            })
        }
    }
}