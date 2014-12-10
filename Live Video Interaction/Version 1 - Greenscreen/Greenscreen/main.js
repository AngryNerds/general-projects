var canvas  = document.getElementById('main_canvas');
canvas.width = 400;
canvas.height = 300;
var ctx  = canvas.getContext('2d');

var x = 10, vx = 10;

var greenscreen = new Image();
greenscreen.src = 'http://fc09.deviantart.net/fs38/i/2008/342/0/7/Green_Construction_Paper_by_kizistock.jpg';

var FPS = 12; //The minimum required to create the illusion of movement.

var udpater = setInterval(function() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawBG();
    drawBox();
    detectBox();
}, 1000/FPS);

function drawBG() {
    ctx.drawImage(greenscreen, 0, 0);
    //ctx.fillStyle = 'rgb(20, 120, 20)';
    //ctx.fillRect(0, 0, canvas.width, canvas.height);
}

function drawBox() {
    ctx.fillStyle = 'rgb(10, 10, 10)';
    ctx.fillRect(x, 20, 10, 10);
    if(x > canvas.width || x < 0) {
        vx = -vx;
    }
    x += vx/FPS;
}

function detectBox() {
    var imgd = ctx.getImageData(0, 0, 400, 300);
    var pix = imgd.data;
    
    for(var i = 0, n = pix.length; i<n; i+=4) {
        var green = pix[i+1] > pix[i]+10 && pix[i+1] > pix[i+2]+10;
        if(!green) {
            pix[i  ] = 255 - pix[i  ]; // red
            pix[i+1] = 255 - pix[i+1]; // green
            pix[i+2] = 255 - pix[i+2]; // blue
        }
    }
    
    ctx.putImageData(imgd, 0, 0);
}