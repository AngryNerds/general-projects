class Home extends Place {
  boolean occupied = false;
  
  public Home(float x, float y) {
    super(x,y);
  }
  
  public boolean isOccupied() {
    return occupied;
  }
  
  public void setOccupied() {
    occupied = true;
  }
  
  public void update() {
    fill(0);
    rect(p.x, p.y, 30, 30);
  }
  
}
