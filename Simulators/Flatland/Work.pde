class Work extends Place {
  boolean occupied = false;
  
  public Work(float x, float y) {
    super(x,y);
  }
  
  public boolean isOccupied() {
    return occupied;
  }
  
  public void setOccupied() {
    occupied = true;
  }
  
  public void update() {
    fill(200, 0, 0);
    rect(p.x, p.y, 40, 40);
  }
  
}
