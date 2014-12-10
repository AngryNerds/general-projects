var output, outgoing, sendButton;
var websocket;

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

var server = "../server.html", DANIELS_HAMACHI = "5.77.142.133", local = "127.0.0.1";

//var username = prompt("Enter your username");
var username = "John";

function send_echo(e) {
    websocket.send(username + ": " + outgoing.value);
    writeToScreen("SENT: " + outgoing.value);
}

function onOpen(evt) {
    writeToScreen("CONNECTED");
}

function onMessage(evt) {
    writeToScreen("RECEIVED: " + evt.data);
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function init() {
    output = document.getElementById("output");
    outgoing = document.getElementById("outgoing");
    sendButton = document.getElementById("sendButton");
    sendButton.onclick = send_echo;
    
    var wsUri = "ws://" + server + ":4444";
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt); };
    websocket.onmessage = function(evt) { onMessage(evt); };
    websocket.onerror = function(evt) { onError(evt); };
}

window.addEventListener("load", init, false);