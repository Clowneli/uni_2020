public class natural_ob{
  float xPos;
  float size;
  PImage image;
  float yPos = 350;
  float a = 0;
  
  //constructor method
  natural_ob(float x){
    image = loadImage("assets/rocks/rock"+int(random(1,4))+".png");
    this.xPos = x;
  }
  
  //method to draw object or move it if it has passed the player
  void drawOb(){
    xPos += a;
    if(xPos < -image.width*2){
      xPos = random(2000,10000);
    }
    image(image,xPos,yPos-image.height,image.width*2,image.height*2);
  }
  
  //Accelerate method (gives illusion of buggy moving)
  void accelerate(float t){
    a+=t;
  }

}
