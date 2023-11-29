var credentials = "AmmJKyxVf-6eaa2iHK_GT4J0wH58FQbLciGKGgSMYZWJSjeZ8x5Tc67SrzWnTLmQ";
var bingMap = null;
var selectedPin = null;
const pinsPointSale = [];
const pinsGarage = [];

function initializeBingMap() {
    if (navigator.geolocation) {
        const options = {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0
        };
        navigator.geolocation.getCurrentPosition(
            function (position) {
                GetMap(position);
            },
            function (error) {
                alert(error);
                GetMap(null);
            },
            options
        );
    } else {
        GetMap(null);
    }
}

function GetMap(position) {
    var lat = null;
    var long = null;

    if (position != null) {
        lat = position.coords.latitude;
        long = position.coords.longitude;
    } else {
        lat = -34.670560;
        long = -58.562780;
    }

    if(viewName === "parking-register") {
        document.getElementById("lat").value = lat;
        document.getElementById("ln").value = long;
    }

    bingMap = new Microsoft.Maps.Map("#myMap", {
        credentials: credentials,
        center: new Microsoft.Maps.Location(lat, long),
        mapTypeId: Microsoft.Maps.MapTypeId.road,
        zoom: 15
    });

    var center = bingMap.getCenter();

    const url = `https://dev.virtualearth.net/REST/v1/Locations/${lat},${long}?key=${credentials}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.resourceSets.length > 0 && data.resourceSets[0].resources.length > 0) {
                var address = data.resourceSets[0].resources[0].address.formattedAddress;
                var personalPin = createPin(center,'¡Yo!', address, "PERSONAL");

                setInfoboxOnPin(personalPin);
                clickFunctionToInfoboxPin(personalPin);

                if(viewName === "parking-register"){
                    adjustPersonalPinMap(personalPin);
                }

                bingMap.entities.push(personalPin);
            } else {
                console.error('No se encontró ninguna dirección.');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud: ', error);
        });

    createPinParkingPlaces();

    if(viewName === "home") {
        showParkingPlace('All');
    }
}

function adjustPersonalPinMap(personalPin) {
    Microsoft.Maps.Events.addHandler(bingMap, 'click', function (e) {
        var newCenter = e.location;
        var newLat = newCenter.latitude;
        var newLng = newCenter.longitude;

        personalPin.setLocation(newCenter);

        document.getElementById("lat").value = newLat;
        document.getElementById("ln").value = newLng;

        const newUrl = `https://dev.virtualearth.net/REST/v1/Locations/${newLat},${newLng}?key=${credentials}`;

        fetch(newUrl)
            .then(response => response.json())
            .then(newData => {
                if (newData.resourceSets.length > 0 && newData.resourceSets[0].resources.length > 0) {
                    personalPin.metadata.description = newData.resourceSets[0].resources[0].address.formattedAddress;
                } else {
                    console.error('No se encontró ninguna dirección.');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud: ', error);
            });
        setInfoboxOnPin(personalPin);
    });
}

function createPinParkingPlaces() {
    const parkingPlaces = parkingPlacesData;

    for (let i = 0; i < parkingPlaces.length; i++) {
        const lat = parkingPlaces[i].geolocation.lat;
        const ln = parkingPlaces[i].geolocation.ln;
        const title = parkingPlaces[i].name;

        let pin;

        pin = createPin(new Microsoft.Maps.Location(lat, ln), title, parkingPlaces[i].address, parkingPlaces[i].type);

        setInfoboxOnPin(pin);

        if (viewName === "parking-register" && parkingPlaces[i].type === "PointSale") {
                Microsoft.Maps.Events.addHandler(pin, 'click', function () {
                    for (var j = 0; j < pinsPointSale.length; j++) {
                        pinsPointSale[j].infobox.setOptions({visible: false});
                    }

                    pin.infobox.setOptions({visible: true});

                    if (selectedPin) {
                        var customIconUrl = "/eparking/img/pinIcons/point-icon.png";
                        selectedPin.setOptions({icon: customIconUrl});
                    }

                    selectedPin = pin;
                    var customIconUrlSelected = "/eparking/img/pinIcons/point-icon-selected.png";
                    pin.setOptions({icon: customIconUrlSelected});
                    document.getElementById("pointSale").value = parkingPlaces[i].id;
                    document.getElementById("pointSaleText").textContent = parkingPlaces[i].name + " | " + parkingPlaces[i].address;
                    document.getElementById("feePerHourAmmount").textContent = parkingPlaces[i].feePerHour;
                });
            } else {
                clickFunctionToInfoboxPin(pin);
            }
            if(parkingPlaces[i].type === "PointSale") pinsPointSale.push(pin);
            if(parkingPlaces[i].type === "Garage") pinsGarage.push(pin);
    }
}

function showParkingPlace(type){
    switch(type){
        case "PointSale": bingMap.entities.push(pinsPointSale); break;
        case "Garage": bingMap.entities.push(pinsGarage); break;
        case "All": bingMap.entities.push(pinsPointSale);
                    bingMap.entities.push(pinsGarage);
                    break;
    }
}

function createPin(location, title, description, type) {
    var customIconUrl;
    switch (type) {
        case "PERSONAL":
            customIconUrl = "/eparking/img/pinIcons/loc-icon.png";
            break;
        case "PointSale":
            customIconUrl = "/eparking/img/pinIcons/point-icon.png";
            break;
        case "Garage":
            customIconUrl = "/eparking/img/pinIcons/garage-icon.png";
            break;
        default:
            break;
    }

    var iconOptions = {
        title: title,
        icon: customIconUrl,
    };

    var pin = new Microsoft.Maps.Pushpin(location, iconOptions);
    pin.metadata = {description: description};

    return pin;
}

function setInfoboxOnPin(pin) {
    var infoboxOffset = new Microsoft.Maps.Point(0, -170);

    if (!pin.infobox) {
        var center = pin.getLocation();
        var title = pin.getTitle();
        var description = pin.metadata.description;

        infobox = new Microsoft.Maps.Infobox(center, {
            title: title,
            description: description,
        });

        infobox.setMap(bingMap);
        infobox.setOptions({
            visible: false,
            offset: infoboxOffset
        })
        pin.infobox = infobox;
    } else {
        var newCenter = pin.getLocation();
        var newTitle = pin.getTitle();
        var newDescription = pin.metadata.description;

        infobox.setLocation(newCenter);
        infobox.setOptions({
            title: newTitle,
            description: newDescription,
            visible: false,
            offset: infoboxOffset
        });
        pin.infobox = infobox;
    }
}

function clickFunctionToInfoboxPin(pin) {
    Microsoft.Maps.Events.addHandler(pin, 'click', function () {
        pin.infobox.setOptions({visible: true});
    });
}

function hideParkingPlacePin(){
    bingMap.entities.remove(pinsPointSale);
    bingMap.entities.remove(pinsGarage);
}