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
        console.error('An error occured: ' + error.code);
    }
}
else {
    console.log('getUserMedia not supported in this browser.');
}

video.addEventListener('play', function() {
    video.width = canvas.width = video.offsetWidth;
    video.height = canvas.height = video.offsetHeight;
    
    updater = setInterval(function() {
        ctx.drawImage(video, 0, 0, video.width, video.height);
    }, 1000/FPS);
}, false);