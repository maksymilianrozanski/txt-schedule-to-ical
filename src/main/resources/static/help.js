function init() {
    document.getElementById('fileInput').addEventListener('change', handleFileSelect, false);
}

function handleFileSelect(event) {
    const reader = new FileReader();
    reader.onload = handleFileLoad;
    reader.readAsText(event.target.files[0])
}

function handleFileLoad(event) {
    let input = event.target.result;
    generateCalendar(input);
}

function generateCalendar(inputText) {
    console.log("Inside generateCalendar");
    let request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/api/cal");
    request.setRequestHeader("Content-Type", "text/plain");
    request.send(inputText);
    request.onload = function () {
        console.log("Response code: " + request.status);
        console.log(request.responseText)
    }
}
