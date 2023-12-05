public class CRS {
  float xCont,yCont,x1,y1,x2,y2,xEnd,yEnd;
  int line;
  color col = 145;
  
  //constructor method
  CRS(float xCont,float yCont, float x1, float y1,
  float x2, float y2,float xEnd,float yEnd,int line){
    this.xCont = xCont;
    this.yCont = yCont;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.xEnd = xEnd;
    this.yEnd = yEnd;
    this.line = line;
  }
  //method to draw curve
  void drawCurve(){
    stroke(col);
    strokeWeight(15);
    curve(xCont,yCont,x1,y1,x2,y2,xEnd,yEnd); 
  }
  //method for getting path the car is on
  public int getPath(){
    return line;
  }
  
  //method for getting curve points
  public float getXCont(){return xCont;}
  public float getYCont(){return yCont;}
  public float getX1(){return x1;}
  public float getY1(){return y1;}
  public float getX2(){return x2;}
  public float getY2(){return y2;}
  public float getXEnd(){return xEnd;}
  public float getYEnd(){return yEnd;}
  
}
