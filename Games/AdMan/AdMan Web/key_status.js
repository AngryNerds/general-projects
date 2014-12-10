var keydown = {"up":false, "down":false, "left":false, "right":false};


$(document).keydown(function(e){

    var keyCode = e.keyCode || e.which,

    arrow = {left: 37, up: 38, right: 39, down: 40 };


    switch (keyCode) {

        case arrow.left:

            keydown.left = true;

            console.log("<");

        break;

        case arrow.up:

            keydown.up = true;

            console.log("^");

        break;

        case arrow.right:

            keydown.right = true;

            console.log(">");

        break;

        case arrow.down:

            keydown.down = true;

            console.log("down");

        break;

  }

});



$(document).keyup(function(e){

    var keyCode = e.keyCode || e.which,

    arrow = {left: 37, up: 38, right: 39, down: 40 };


    switch (keyCode) {

        case arrow.left:

            keydown.left = false;

            console.log("<");

        break;

        case arrow.up:

            keydown.up = false;

            console.log("^");

        break;

        case arrow.right:

            keydown.right = false;

            console.log(">");

        break;

        case arrow.down:

            keydown.down = false;

            console.log("down");

        break;

  }

});