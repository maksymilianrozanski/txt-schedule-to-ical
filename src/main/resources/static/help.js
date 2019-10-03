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
    let request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/api/cal");
    request.setRequestHeader("Content-Type", "text/plain");
    request.send(inputText);
    request.onload = function () {
        if (request.status === 200) {
            download("generatedSchedule.ical", request.responseText);
            document.getElementById('result').innerHTML = "Success";
            document.getElementById('result').style.color = "Green";
        } else {
            setError();
        }
    };
    request.onerror = function () {
        setError()
    };
}

function setError() {
    document.getElementById('result').innerHTML = "Something went wrong";
    document.getElementById('result').style.color = "DarkRed";
}

function download(filename, text) {
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);
    element.style.display = "none";
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
}
