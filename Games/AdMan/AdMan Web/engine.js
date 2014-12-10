var GRAV = 48;
var FPS = 30;
var onGround = true;
var platforms = new Array();
for(var i=0; i<3; i++) {
    platforms[i] = {
        "DOM": document.getElementById(i)
    };
    platforms[i].x = i*100 + 80;
    platforms[i].y = i*100 + 40;
    
    platforms[i].DOM.style.bottom = platforms[i].y + "px";
    platforms[i].DOM.style.left = platforms[i].x + "px";
}

var player = {
    "DOM": document.getElementById("player"),
	"x": 0,
	"y": 0,
	"vx": 0,
	"vy": 0
};
player.x = player.y = 0;

var platforms = [];

function initGame() {
	//player.standing = new Image();
	//player.runningLeft.src = "player.png";

/*
	for(var i=0; i<10; i++) {
		player.runningLeft[i] = new Image();
		player.runningLeft[i].src = "graphics/player/left_"+i;
		player.runningRight[i] = new Image();
		player.runningright.src[i] = "player_right_"+i;
	}*/

}

function doPhysics() {
    //Ye Olde Gravity
    if(player.y > 0 && !onGround) {
        player.vy -= GRAV / FPS;
    }
    if(player.y <= 0 && player.vy < 0) {
        player.vy = 0;
    }
    if(onGround && player.vy < 0) {
        player.vy = 0;
    }
    
    player.x += player.vx / FPS;
    player.y += player.vy / FPS;
    
    player.DOM.style.bottom = player.y + "px";
    player.DOM.style.left = player.x + "px";
    
    //Let's do some COLLISION DETECTION
    //...or not.
    for(var i=0; i<platforms.length; i++) {
        var platform = platforms[i];
        if(player.vy <=0 && player.x > platform.x && player.x < platform.x + 75 /*the width*/ && player.y == platform.y + 25 /*the height*/) {
            player.vy = 0;
        }
        else {
            onGround = false;
        }
    }
}

function cameraFollow() {/*
    if(player.x > window.innerWidth * 0.8) {
        window.scrollBy(200, 0);
    }
*/}

function handleInput() {
    
    if(keydown.left) {
        player.vx = -160;
    }
    if(keydown.right) {
        player.vx = 160;
    }
    if(keydown.up && player.vy==0) {
        player.vy = 130;
    }
    if(keydown.down) {
        player.vy = -130;
    }
}

function updateGame() {
    console.log("blah");
	doPhysics();
    cameraFollow();
    handleInput();
}

//Let's do this!!!!!
var updater = setInterval(updateGame, 1000/FPS);