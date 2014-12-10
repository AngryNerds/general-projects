function loadNews() {
	document.getElementById("loadNewsButton").style.visibility = "hidden";
	var node = document.getElementById("nerdnews");
	while(node.firstChild) {
		node.removeChild(node.firstChild);
	}
	var loading = document.createElement("IMG");
	loading.setAttribute("src", "images/loading.gif");
	loading.setAttribute("alt", "Loading...");
	loading.setAttribute("height", "32");
	loading.setAttribute("width", "32");
	node.appendChild(loading);
	var loadingtxt = document.createElement("SPAN");
	loadingtxt.setAttribute("style", "position: relative; bottom: 10px;");
	loadingtxt.setAttribute("id", "loadingTXT");
	loadingtxt.appendChild(document.createTextNode("     Loading NerdNews..."));
	node.appendChild(loadingtxt);
	requestNerdNews();
}

function Item(body, date) {
	this.body = body;
	this.date = date;
}

var news = [];

var request = null;

function requestNerdNews() {
	request = null;
	if (window.XMLHttpRequest) {
		try {
			request = new XMLHttpRequest();
		} catch(e) {
			request = null;
		}
	}
	request.onreadystatechange = handleRequest;
	request.open("GET", "nerdnews.txt", true);
	request.send(null);
}

function handleRequest() {
	var readyState = request.readyState;
	var stateLookup = ["request not initialized", "server connection established", "request recieved", "processing request", "request finished"];
	document.getElementById("loadingTXT").innerHTML = "     Loading NerdNews... Status: " + stateLookup[readyState] + " (" + readyState + ") - " + request.status;
	if(readyState == 4) {
		document.getElementById("nerdnews").innerHTML = request.responseText
	}
}					