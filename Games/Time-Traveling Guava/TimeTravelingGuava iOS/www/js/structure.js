var FPS = 18;
var GRID_WIDTH = 28;
var GRID_SIZE = canvas.width / GRID_WIDTH;
var GRID_HEIGHT = canvas.height / GRID_SIZE;

/*-----------------------------
    REUSABLE GAME CODE
-----------------------------*/
function Track() {
    
}

function GameObj(x, y) {
    this.x = 0;
    this.y = 0;
    this.width = 15;
    this.height = 15;
    this.vx = 0;
    this.vy = 0;    
    
    this.img;
    this.frameNum = 0;
}

GameObj.prototype.draw = function(ctx) {
    //ctx.drawImage(this.img, (frameNum%(img.width/width))*width, 0, this.x, this.y, this.width, this.height);
    if(this.x > 0 && this.y > 0 && this.x < canvas.width && this.y < canvas.height) {
        ctx.fillStyle = "#FFF";
        ctx.fillRect(this.x, this.y, this.width, this.height);
    }
};

GameObj.prototype.move = function() {
    this.x += this.vx / FPS;
    this.y += this.vy / FPS;
};

GameObj.prototype.update = function(ctx) {
    this.move();
    this.draw(ctx);
};


/*-----------------------------
    LASER GAME CODE
-----------------------------*/

function Pulse(x, y, vx, vy) {
    GameObj.call(this);
    
    //Set Location & Velocity
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
}
Pulse.prototype = new GameObj();
Pulse.prototype.constructor = Pulse;

function Laser(x, y) {  
    this.x = x;
    this.y = y;
    
    this.pulses = [];
    
    this.strength = 0;
    this.speed = 0;
    this.range = 0;
    this.delay = 0;
    this.color = "#83b1be";
}

Laser.prototype.findTargets = function(enemies) {
    var withinRange = [];
    
    for(var i = 0; i < enemies.length; i++) {
        // if enemy within range, add them to the list
    }
};

Laser.prototype.fireAt = function(target, pulseList) {
    
    //Draw a line from the laser to the object.
    ctx.beginPath();
    
    ctx.moveTo(this.x, this.y);
    ctx.lineTo(target.x, target.y);
    
    ctx.strokeStyle = "#C1F0F6";
    ctx.stroke();
    
    //pulseList.push(new Pulse(this.x));
};

Laser.prototype.drawPulses = function(ctx) {
    
};


/*-----------------------------
    JELLYFISH CODE
-----------------------------*/

var jellyfishList = [];

function Jellyfish(x, y) {
    //OOP
    GameObj.call(this);
    
    this.laser = new Laser(this.x, this.y);
    
    this.health;
    
    jellyfishList.push(this);
}
Jellyfish.prototype = new GameObj();
Jellyfish.prototype.constructor = Jellyfish;


function WeakJF(x, y) {
    Jellyfish.call(this);
    
    this.laser.strength = 20;
    this.laser.range = 25;
    this.laser.delay = 0.4;
}
WeakJF.prototype = new Jellyfish();
WeakJF.prototype.constructor = WeakJF;

/*-----------------------------
    TOWER CODE
-----------------------------*/

function Tower(x, y) {
    //OOP
    GameObj.call(this);
    
    this.laser = new Laser(this.x, this.y);
}
Tower.prototype = new GameObj();
Tower.prototype.constructor = Tower;

Tower.prototype.place = function() {
    this.x = Math.round(this.x / GRID_SIZE);
    this.y = Math.round(this.y / GRID_SIZE);
}