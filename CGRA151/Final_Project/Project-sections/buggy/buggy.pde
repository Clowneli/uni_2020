class Buggy {
  float xPos = 100;
  float yPos = 275;
  boolean active = true;
  PImage sprite = loadImage("assets/buggy.png");
  float gravity = 5;
  boolean riseFall = false; //false when falling
  float jumpHeight = 100;
  float a = 0; // acceleration
  
  //draw buggy
  void drawBuggy(){
    image(sprite,xPos,yPos,100,75);
    drawWheels();
  }
  
  void drawWheels(){
    fill(0);
    ellipse(xPos+20,yPos+70,28,28);
    ellipse(xPos+50,yPos+70,28,28);
    ellipse(xPos+80,yPos+70,28,28);
  }
  
  void accelerate(float t){
    a += t;
  }
  
  void jump(){
    if (yPos == 275){
      riseFall = true;
    }
  }
  
  void gravity(){
    if(riseFall){
      yPos -= gravity;
      if (yPos < 275-jumpHeight){
        riseFall = false;
      }
    }
    if(!riseFall){
      yPos += gravity;
      if (yPos > 275){yPos = 275;}
    }
    xPos += a;
  }
}
