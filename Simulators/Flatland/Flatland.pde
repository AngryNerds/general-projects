ArrayList<Shape> shapes;
ArrayList<Home> homes;
ArrayList<Work> works;

final int dayLength = 60;
final int daySeg = dayLength / 4; //one day per min
final int MORNING = 0, DAY = 1, EVENING = 2, NIGHT = 3;
int time;

void setup() {
  size(640, 480);
  
  shapes = new ArrayList<Shape>();
  homes = new ArrayList<Home>();
  works = new ArrayList<Work>();
}

void draw() {
  time = (int) second() / daySeg;
  
  background(#EEEEEE);
  
  for(Home home: homes) {
    home.update();
  }
  for(Work work: works) {
    work.update();
  }
  
  for(int i = 0; i < shapes.size(); i++) {
    Shape s = shapes.get(i);
    s.update();
    
    for(int j = 0; j < i; j++) {
      Shape s2 = shapes.get(j);
      
      if(s.collisionWith(shapes.get(j))) {
        if(s.notAlreadyCollidingWith(s2) && s.getSides() == s2.getSides()) {
          Shape baby = new Shape( s.getX(), s.getY(), s.getSides() + 1,
           lerpColor( s.getColor(), s2.getColor(), .5f ) );
          shapes.add( baby );
          s.acknowledgeCollision(s2, baby);
          s2.acknowledgeCollision(s, baby);
          baby.acknowledgeCollision(s, s2);
        }
      }
      else {//if we are done colliding
        //s.acknowledgeCollision(null, null);
        //So that it can register a collision with the same shape later.
      }
    }
  }
  
  fill(0);
  int yCoord = 10;
  int lineHeight = 25;
  text("Instructions:\n- Click to add shapes." + 
  "\n- Shift click to add houses." +
  "\n- Option click to add Work places.", 16, yCoord);
  yCoord += lineHeight * 3;
  text("Current time: " + second(), 16, yCoord);
  yCoord += lineHeight;
  text(String.format("%,d shapes -- %,d houses -- %,d works", shapes.size(), homes.size(), works.size()), 16, yCoord);
}

void mousePressed() {
  if(keyPressed && key == CODED) {
    if(keyCode == SHIFT) {
      homes.add( new Home(mouseX, mouseY) );
    }
    else if(keyCode == ALT) {
      works.add( new Work(mouseX, mouseY) );
    }
  }
  else {
    shapes.add( new Shape(mouseX, mouseY) );
  }
}
