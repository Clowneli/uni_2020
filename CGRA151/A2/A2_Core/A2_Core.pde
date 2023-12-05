//Sets initial variables
size(800,600);
final float LEFTWALL = 0;
final float RIGHTWALL = width;
final float ROOF = 0;
final float FLOOR = height;
ball ball = new ball();
bat bat = new bat();

//setup method
void setup(){
  size(800,600);
  frameRate(60);
  
}

/**
*draw method:
*Resets the background and updates the ball and bat, Checks to see if touching and bounce the ball if so
**/

void draw(){
  background(255);
  bat.drawBat(mouseX,mouseY);
  ball.move();
  if(ball.isTouchingX(mouseX) && ball.isTouchingY(mouseY)){
    ball.bounce();
  }
  
}

//ball class
class ball{
  //Variables for ball
  float xPos = 400;
  float yPos = 300;
  float xVel = 5;
  float yVel = 5;
  float radius = 25;
  //draws the ball
  void drawBall(){
    fill(135);
    ellipse(xPos,yPos,radius,radius);
  }
  //moves the ball (updates y/x position)
  void move(){
    xPos += xVel;
    yPos += yVel;
    hasHitWall();
    drawBall();
  }
  //finds if the ball has hit a wall then changes the direction
  void hasHitWall(){
    //a handfull of if statements for each wall
    if(xPos <= LEFTWALL){
      xVel *= -1;
      xPos += 10;
    }
    if(xPos >= width){
      xVel *= -1;
      xPos -= 10;
    }
    if (yPos > height){
      this.bounce();
      yPos -= 10;
    }
    if (yPos < ROOF){
      bounce();
      yPos += 10;
    }
  }
  
  //bounce method
  void bounce(){
    yVel *= -1;
  }
  //gets co-ords for the ball
  public float getX(){
    return xPos;
  }
  public float getY(){
    return yPos;
  }
  // checks if the ball is touching the bat
  public boolean isTouchingX(float x){
    if ((xPos > x-50) && (xPos < x+50)){
      return true;
    } 
    else{return false;}
  }
  public boolean isTouchingY(float y){
    if ((yPos > y-7.5) && (yPos < y+7.5)){
      return true;
    } 
    else{return false;}
  }
}

//bat class
class bat{
  float xPos =0;
  float yPos =0;
  
  //draw bat method
  void drawBat(float x, float y){
    xPos = x;
    yPos = y;
    fill(0);
    rect(xPos-50,yPos-7.5,100,15);
  }
  //gets co-ords for the bat
  public float getX(){
    return xPos;
  }
  public float getY(){
    return yPos;
  }
}
