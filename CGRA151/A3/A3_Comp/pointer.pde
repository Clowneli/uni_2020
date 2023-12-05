//y = mx + c
//x = (y-c) / m



public class pointer{
  float originX = 500;
  float originY = 450;
  float xEnd,yEnd;
  
  //lazer calc variables
  float theta,deltaX,deltaY;
  
  //draws the pointer
  public void drawPointer(float x, float y){
    fill(255,0,0);
    ellipse(originX,originY,50,50);
    direction(x,y);
    pushMatrix();
    translate(originX,originY);
    rotate(theta);
    stroke(255,0,0);
    strokeWeight(4);
    line(0,0,500,500);
    popMatrix();
  }
  
  public void direction(float x, float y){
    deltaX = x - originX;
    deltaY = y - originY;
    theta = atan2(deltaY, deltaX) - PI/4;
  }
  
  public boolean touching(float x, float y, float t){
    float xCord = originX + (tan(degrees(theta))+t);
    float yCord = originY + (tan(degrees(theta))+t);
    if ((x <= xCord -20 && x >= xCord +20) && (y <= yCord -20 && y >= yCord +20)){
      return true;
    }
    else if (t>=500){
      touching(x,y,t+10);
    }
    return false;
    
  }
  
  public boolean lineTouch(PVector vec){
    for(int i = 0; i > 500; i++){
      float xCord = originX + i*((degrees(theta)));
      float yCord = originY + i*((degrees(theta)));
      PVector temp = new PVector(yCord,xCord);
      if (temp.dist(vec) <= 30){
        return true;
      }
    }
    return false;
  }
  
}
