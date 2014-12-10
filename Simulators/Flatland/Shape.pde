class Shape {
  
  private static final int radius = 20;
  
  private int sides;
  
  private Home home;
  private Work work;
  
  private float rotation;
  private PVector pos, vel;
  private final color c;
  
  private Shape[] acknowledged;
  
  public Shape(float x, float y, int sides, color c, Home home, Work work) {
    this.sides = sides;
    
    pos = new PVector(x, y, 0);
    float v = 3f;
    vel = new PVector(random(-v, v), random(-v, v), 0);
    
    acknowledged = new Shape[2];
    
    this.c = c;
    this.home = home;
    this.work = work;
  }
  
  public Shape(float x, float y) {
    this(x, y, 3, color(random(255), random(255), random(255)));
  }
  
  public Shape() {
    this(random(width), random(height));
  }
  
  public float getRotation() {
    return rotation;
  }
  
  public void update() {
    pos.x = (pos.x % width) + vel.x;
    pos.y = (pos.y % height) + vel.y;
    
    fill(c);
    noStroke();
    polygon( sides, pos.x, pos.y, radius );
    
    switch(time) {
      case MORNING:
        moveTo(work);
        break;
      case DAY:
        vel.x = vel.y = 0;
        break;
      case EVENING:
        moveTo(home);
        break;
      case NIGHT:
        vel.x = vel.y = 0;
        break;
    }
  }
  
  private void moveTo(Place p) {
    vel.x = dist(pos.x, 0, p.getP().x, 0) / daySeg;
    vel.y = dist(0, pos.y, 0, p.getP().y) / daySeg;
  }
  
  public boolean collisionWith(Shape s) {
    return dist( pos.x, pos.y, s.getX(), s.getY() ) < radius * 2;
  }
  
  public boolean notAlreadyCollidingWith(Shape s) {
    return s != acknowledged[0] && s != acknowledged[1];
  }
  
  public float getX() {
    return pos.x;
  }
  
  public float getY() {
    return pos.y;
  }
  
  public int getSides() {
    return sides;
  }
  
  public void acknowledgeCollision(Shape s1, Shape s2) {
    acknowledged[0] = s1;
    acknowledged[1] = s2;
  }
  
  private void polygon(int n, float cx, float cy, float r)
  {
    float angle = 360.0 / n;
  
    beginShape();
    for (int i = 0; i < n; i++)
    {
      vertex(cx + r * cos(radians(angle * i)),
        cy + r * sin(radians(angle * i)));
    }
    endShape(CLOSE);
  }
  
  public color getColor() {
    return c;
  }
}
