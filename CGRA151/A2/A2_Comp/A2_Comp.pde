//Sets initial variables
size(800,600);
final float LEFTWALL = 0;
final float RIGHTWALL = width;
final float ROOF = 0;
final float FLOOR = height;
ball ball = new ball();
bat bat = new bat();
ArrayList<block> blocks;

//setup method
void setup(){
  noStroke();
  size(800,600);
  frameRate(60);
  //sets the blocks into place
  blocks = new ArrayList<block>();
  for(float Y=0; Y < 6; Y++){
    for(float X=0; X < width;X+=width/4){
      block temp = new block();
      temp.setPos(X,Y*50);
      blocks.add(temp);
    }
  }
  
}

/**
*draw method:
*Resets the background and updates the ball and bat, Checks to see if touching and bounce the ball if so
**/

void draw(){
  background(255);
  bat.drawBat(mouseX,mouseY);
  ball.move();
  for(block block:blocks){
    if(ball.isTouchingBlock(block.getX(),block.getY())){
      if(block.isActive()){
        if((block.getX()+5 >= ball.getX()) 
          || (block.getX()+width/4-5 <= ball.getX())){
          ball.bounceOffWall();}
        else if(ball.cornerCheck(block.getX(),block.getY())){ball.bounce();ball.bounceOffWall();}
        else{ball.bounce();}
        
          ball.move();
        block.takeDamage();
      }
    }
    block.drawBlock();
  }
  if(ball.isTouchingBat(mouseX,mouseY)){
    ball.bounce();
    if(ball.isTouchingSideBat(mouseX)){
      ball.bounceOffWall();
    }
  }
  
}

//ball class
class ball{
  //Variables for ball
  float xPos = 400;
  float yPos = 300;
  float xVel = 5;
  float yVel = 5;
  float radius = 15;
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
      bounceOffWall();
      xPos += 10;
    }
    if(xPos >= width){
      bounceOffWall();
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
    yPos += this.directionY();
  }
  void bounceOffWall(){
    xVel *= -1;
    xPos += this.directionX();
  }
  //gets co-ords for the ball
  public float getX(){
    return xPos;
  }
  public float getY(){
    return yPos;
  }
  // checks if the ball is touching the bat
  public boolean isTouchingBat(float x, float y){
    if ((xPos > x-50) && (xPos < x+50)){
      if ((yPos > y-7.5) && (yPos < y+7.5)){
        return true;
      } 
      else{return false;}
    } 
    else{return false;}
  }
  public boolean isTouchingSideBat(float x){
    if ((xPos <= x-45) || (xPos >= x+45)){
      return true;
    }
    else{ return false;}
  }
  //checks if ball is touching a block
  public boolean isTouchingBlock(float x,float y){
    if ((xPos > x) && (xPos < x+width/4)){
      if ((yPos > y) && (yPos < y+50)){
      return true;
    } 
    else{return false;}
    } 
    else{return false;}
  }
  //check for touching corner of block
  public boolean cornerCheck(float x, float y){
    if ((xPos == x) || (xPos == x+width/4)){
      if ((yPos == y) || (yPos == y+50)){
        return true;
      }
      return false;
    }
    else{ return false;}
  }
  public float directionX(){
    if (xVel < 0){return -5;}
    else{return 5;}
  }
  public float directionY(){
    if (yVel < 0){return -5;}
    else{return 5;}
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

/** block method
*this is the code for each individual block that the ball can hit
*/
class block{
  color green = color(0, 255, 0);
  color red = color(255,0,0);
  color yellow = color(255,255,0);
  float xPos;
  float yPos;
  public float top = width/4;
  public float side = 50;
  int damageState = 3;
  boolean isActive = true;
  void setPos(float x, float y){
    this.xPos = x;
    this.yPos = y;
  }
  //updates the current damage state and turns the block off if there is no life left
  void takeDamage(){
    damageState -= 1;
    if(damageState == 0){
      isActive = false;
    }
  }
  //returns a colour based on the damage state of the block
  void setColour(){
    if(damageState == 3){
      fill(green);
    }
    else if(damageState == 2){
      fill(yellow);
    }
    else if(damageState == 1){
      fill(red);
    }
    else{isActive = false;fill(0,0,0,0);}
  }
  //draws the block
  void drawBlock(){
    this.setColour();
    rect(xPos,yPos,top,side);
  }
  //is active
  public boolean isActive(){
    return isActive;
  }
  public float getX(){
    return xPos;
  }
  public float getY(){
    return yPos;
  }
  
  
}
