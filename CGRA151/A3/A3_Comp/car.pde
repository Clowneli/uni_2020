public class car{
  float xPos,yPos,direct,t,tx,ty;
  int path;
  ArrayList<CRS> crs = new ArrayList<CRS>();
  CRS line;
  PVector location;
  pointer p;
  
  //constructor
  car(int path, ArrayList<CRS> crs, pointer p){
    this.path = path;
    this.crs = crs;
    this.p = p;
  }
  
  void drawCar(){
    getX();
    getY();
    getCol(p);
    strokeWeight(4);
    stroke(0);
    
    //changes orientation of car to properly follow line and dirction
    pushMatrix();
    translate(xPos,yPos);
    rotate(getTangent());
    rect(-20,-10,40,20);
    popMatrix();
  }
  
  
  /**
  ** helper methods for draw
  */
  public void getT(){
    this.t = (frameCount/100.0)%1;
  }
  
  public void getX(){
    this.line=crs.get(path-1);
    this.getT();
    this.xPos = curvePoint(line.getXCont(),line.getX1(),line.getX2(),line.getXEnd(),t);
  }
  
  public void getY(){
    this.line=crs.get(path-1);
    this.getT();
    this.yPos = curvePoint(line.getYCont(),line.getY1(),line.getY2(),line.getYEnd(),t);
    endOfLine();
  }
  //Progresses car to next line and returns it to firstline if past the last line
  public void nextLine(){
    this.path++;
    if(path>crs.size()){this.path=1;}
  }
  //check for if the car is at end of line
  public void endOfLine(){
    if(t>=1){
      nextLine();
      getX();
      getY();
    }
  }
  //Method for getting the tangent of the curve
  public float getTangent(){
    this.tx = curveTangent(line.getXCont(),line.getX1(),line.getX2(),line.getXEnd(),t);
    this.ty = curveTangent(line.getYCont(),line.getY1(),line.getY2(),line.getYEnd(),t);
    return atan2(ty,tx);
  }
  
  //method for getting car col;
  public void getCol(pointer p){
    location = new PVector(xPos,yPos);
    if (p.lineTouch(location)){
      fill(255,0,0);
    }
    else{fill(0);}
  }
}
