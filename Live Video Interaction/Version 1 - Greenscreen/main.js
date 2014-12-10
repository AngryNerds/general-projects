var video  = document.getElementById('sourcevid');
var canvas  = document.getElementById('canvas');
var ctx  = canvas.getContext('2d');

canvas.width = video.width;
canvas.height = video.height;

var boxes = [];

var FPS = 30;


if(navigator.getUserMedia) {
    navigator.getUserMedia('video', streamSuccess, streamError);
}
else {
    console.log('getUserMedia not supported in this browser.');
}

initBoxes();




function streamSuccess(stream) {
    video.src = stream;
    ctx.drawImage(video, 0, 0, video.width, video.height);
}

function streamError(error) {
    console.error('An error occured: ' + error.code);
}

function initBoxes() {
    
}

function update() {
    ctx.drawImage(video, 0, 0, video.width, video.height);
}

function doPhysics() {
    for(var i=0; i<boxes.length; i++) {
        
    }
}

function detectCollisions() {
    
}