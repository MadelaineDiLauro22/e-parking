const parking = document.getElementById("parkingType");
const parkingDateInput = document.getElementById("parkingDate");
const enableAlarm = document.getElementById("enableAlarm");
const typeDateElement = document.getElementById("typeDate");
const calculateByHoursElement = document.getElementById("calculateByHours");
const calculateByAmountElement = document.getElementById("calculateByAmount");
const feePerHourElement = document.getElementById("feePerHour");
const resultCalculatedElement = document.getElementById("resultCalculated");
const amountElement = document.getElementById("amount");
const hoursElement = document.getElementById("hours");
const resultElement = document.getElementById("result");
const hoursTotalElement = document.getElementById("hoursTotal");
const resultHoursElement = document.getElementById("resultHours");
const feePerHourAmountElement = document.getElementById("feePerHourAmmount");

//var now = new Date(Date.now() - (3 * 60 * 60 * 1000));
//var formattedNow = now.toISOString().slice(0, 16);

//parkingDateInput.value = formattedNow;

function renderForms() {
    let divGarage = document.getElementById("typeGarage");
    let divPointSale = document.getElementById("pointSaleDiv");

    switch (parking.value) {
        case "GARAGE":
            divGarage.style.display = "block";
            divPointSale.style.display = "none";
            enableAlarm.checked = false;
            renderAlarmDate();
            hideParkingPlacePin();
            showParkingPlace("Garage");
            break;
        case "POINT_SALE":
            divPointSale.style.display = "block";
            divGarage.style.display = "none";
            enableAlarm.checked = false;
            renderAlarmDate();
            hideParkingPlacePin();
            showParkingPlace("PointSale");
            break;
        case "STREET":
            divPointSale.style.display = "none";
            divGarage.style.display = "none";
            enableAlarm.checked = false;
            renderAlarmDate();
            hideParkingPlacePin();
            break;
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const modal = new bootstrap.Modal(document.getElementById("myModal"));
    const firstRadio = document.getElementById("firstRadio");
    const secondRadio = document.getElementById("secondRadio");

    secondRadio.addEventListener("change", function () {
        if (secondRadio.checked) {
            modal.show();
        } else {
            modal.hide();
        }
    });

    const acceptButton = document.querySelector("[data-bs-dismiss='modal'][data-bs-save-changes]");
    acceptButton.addEventListener("click", () => {
        modal.hide();
        firstRadio.disabled = false;
    });

    modal._element.addEventListener("hidden.bs.modal", () => {
        firstRadio.disabled = false;
    });
});

function formatExpiration() {
    const input = document.getElementById("expiration");
    const value = input.value;

    const numericValue = value.replace(/\D/g, "");

    const month = numericValue.slice(0, 2);
    const year = numericValue.slice(2, 4);

    if (parseInt(month) < 1 || parseInt(month) > 12) {
        input.setCustomValidity("El mes debe estar entre 01 y 12");
    } else {
        input.setCustomValidity("");
    }

    if (numericValue.length >= 2) {
        input.value = `${month}/${year}`;
    } else {
        input.value = numericValue;
    }
}

function validateForm() {
    var pointSale = document.getElementById("pointSale").value;
    var checkRegisterAlarm = document.getElementById("enableAlarm").checked;
    var dateAlarm = document.getElementById("alarmDate").value;

    if (parking.value === "POINT_SALE" && pointSale === "") {
        alert("Por favor, selecciona un punto de pago antes de registrar el estacionamiento medido.");
        return false;
    } else if (checkRegisterAlarm && dateAlarm == "") {
        alert("Por favor, selecciona hora y fecha para la alarma.");
        return false;
    }
    return true;
}


function renderAlarmDate() {
    const alarmOptions = document.getElementById("alarmOptions");

    if (enableAlarm.checked) {
        let parking = document.getElementById("parkingType").value;

        switch (parking) {
            case "GARAGE":
                alarmOptions.style.display = "none";
                typeDateElement.style.display = "block";
                calculateByHoursElement.style.display = "none";
                calculateByAmountElement.style.display = "none";
                feePerHourElement.style.display = "none";
                resultCalculatedElement.style.display = "none";
                break;
            case "POINT_SALE":
                typeDateElement.style.display = "block";
                alarmOptions.style.display = "block";
                break;
            case "STREET":
                alarmOptions.style.display = "none";
                typeDateElement.style.display = "block";
                calculateByHoursElement.style.display = "none";
                calculateByAmountElement.style.display = "none";
                feePerHourElement.style.display = "none";
                resultCalculatedElement.style.display = "none";
                break;
        }
    } else {
        alarmOptions.style.display = "none";
        typeDateElement.style.display = "none";
    }
}

function renderAlarmType(selectedOption) {
    typeDateElement.style.display = "none";
    calculateByHoursElement.style.display = "none";
    calculateByAmountElement.style.display = "none";

    switch (selectedOption) {
        case "NORMAL":
            amountElement.value = 0;
            hoursElement.value = 0;
            resultElement.textContent = 0;
            hoursTotalElement.textContent = 0;
            typeDateElement.style.display = "block";
            feePerHourElement.style.display = "none";
            resultCalculatedElement.style.display = "none";
            break;
        case "AMOUNT_HS":
            calculateByHoursElement.style.display = "block";
            feePerHourElement.style.display = "block";
            resultCalculatedElement.style.display = "block";
            resultHoursElement.style.display = "none";
            break;
        case "AMOUNT_DESIRED":
            calculateByAmountElement.style.display = "block";
            feePerHourElement.style.display = "block";
            resultHoursElement.style.display = "block";
            resultCalculatedElement.style.display = "none";
            break;
        default:

            break;
    }
}

function calculateByHours() {
    const hoursForCalculate = parseFloat(hoursElement.value);
    const hourlyRate = parseFloat(feePerHourAmountElement.textContent);
    resultElement.textContent = (hoursForCalculate * hourlyRate);
}

function calculateByAmount() {
    const amountForCalculate = parseFloat(amountElement.value);
    const hourlyRate = parseFloat(feePerHourAmountElement.textContent);
    hoursTotalElement.textContent = (amountForCalculate / hourlyRate);
}