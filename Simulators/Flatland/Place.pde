public abstract class Place {
  
  protected PVector p;
  
  public Place(float x, float y) {
    p = new PVector(x, y, 0f);
  }
  
  public PVector getP() {
    return p;
  }
  
}
