public class natural_ob{
  float xPos;
  float size;
  PImage image;
  float yPos = 350;
  float a = 0;
  boolean isRed = false;
  
  //constructor method
  natural_ob(float x, String rock){
     
     if(rock.contains("red")){
       image = loadImage("assets/rocks/rock"+int(random(1,3))+".png");
       isRed = true;
     }
     else{image = loadImage("assets/rocks/rock"+int(random(3,6))+".png");}
    this.xPos = x;
  }
  
  
  //method to draw object or move it if it has passed the player
  void drawOb(){
    xPos += a*1.5;
    if(xPos < -image.width*2){
      xPos = random(2000,10000);
      if (isRed){
        image = loadImage("assets/rocks/rock"+int(random(1,3))+".png");
      }
      else{loadImage("assets/rocks/rock"+int(random(3,6))+".png");}
    }
    
    //only draws if in veiwing window to save processing power.
    if(xPos < width+image.width*2){
      image(image,xPos,yPos-image.height*1.5,image.width*2,image.height*2);
    }
  }
  
  //Accelerate method (gives illusion of buggy moving)
  void accelerate(float t){
    a+=t;
    if(a > 0){
      a=0;
    }
    if(a > -6){a=-6;}
  }
  
  //resets rock if shot and is not a red rock
  void hitRock(){
    if(!isRed){
      xPos = random(2000,10000);
      loadImage("assets/rocks/rock"+int(random(3,6))+".png");
    }
  }
  
  
  //getter methods
  public float getX(){return xPos;}
  public float getY(){return yPos-image.height*1.5;}
  public float getWidth(){return image.width*2;}
  public float getHeight(){return image.height*2;}



}
