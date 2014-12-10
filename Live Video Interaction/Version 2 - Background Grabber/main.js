var video  = document.getElementById('sourcevid');
var canvas  = document.getElementById('main_canvas');
var ctx  = canvas.getContext('2d');
var updater;

var FPS = 12;

if(navigator.getUserMedia) {

    navigator.getUserMedia('video', streamSuccess, streamError);
    
    function streamSuccess(stream) {
        video.src = stream;
    }

    function streamError(error) {
        console.error('An error occured. [CODE: ' + error.code + ']');
    }
}
else {
    console.log('getUserMedia not supported in this browser.');
}

video.addEventListener('play', function() {
    video.width = canvas.width = video.offsetWidth;
    video.height = canvas.height = video.offsetHeight;
    update();
}, false);

function update() {
    ctx.drawImage(video, 0, 0, video.width, video.height);
    draw();
    updater = setTimeout("update()", 1000/FPS);
}

function checkVid() {
    var imgd = ctx.getImageData(0, 0, canvas.width, canvas.height);
    pix = imgd.data;
    
    for(var i=0; i<pix.length; i+=4) {
        
    }
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    ctx.putImageData(imgd, 0, 0);
}