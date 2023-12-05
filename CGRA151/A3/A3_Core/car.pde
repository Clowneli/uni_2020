public class car{
  color col = (0);
  float xPos,yPos,direct,t,tx,ty;
  int path;
  ArrayList<CRS> crs = new ArrayList<CRS>();
  CRS line;
  car(int path, ArrayList<CRS> crs){
    this.path = path;
    this.crs = crs;
  }
  void drawCar(){
    getX();
    getY();
    stroke(0);
    fill(col);
    strokeWeight(4);
    
    //changes orientation of car to properly follow line and dirction
    pushMatrix();
    translate(xPos,yPos);
    rotate(getTangent());
    rect(0-20,0-10,40,20);
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
    endOfLine();
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
  public void configureTri(){
    float listXPos[];
    float listYPos[];
    //listXPos[0] = ;
    
  }
  
}
