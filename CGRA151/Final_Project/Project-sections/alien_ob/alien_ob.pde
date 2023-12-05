public class Alien_ob{
  float xPos;
  float size;
  PImage image;
  float yPos;
  float a = 1;
  int alienType;
  float wait;
  float timer;
  shoot shot;
  
  //constructor method
  Alien_ob(float x,int type){
    image = loadImage("assets/aliens/alien"+type+".png");
    this.wait = x;
    this.alienType = type;
    yPos = random(50,250);
    xPos = -image.width*2;
    timer = 50;
  }
  
  //method to draw object or move it if it has passed the player
  void drawOb(){
    //image(image,500,200-image.height,image.width*2,image.height*2); //test
    //alien 4 is ground unit
    
    
    
    if(wait <= 0){
      image(image,xPos,yPos,image.width*2,image.height*2);
      xPos += abs(a);
      timer--;
      
    }
    if(xPos > width+image.width*2){
      xPos = -image.width*2;
      wait = random(2000,10000);
    }
    wait -= abs(a);
    if(timer==0){
        shot = new shoot(xPos,yPos,a);
        timer = 50;
      }
      if (shot != null){
        shot.drawShoot();
      }
  }
  
  //Accelerate method (moves alien with frame)
  void accelerate(float t){    
    a+=t/2;
    if(a<1){a=1;}    
  }

}



public class shoot{
  float xPos,yPos,a;
  int stage = 0;
  ArrayList<PImage> chains =  new ArrayList<PImage>();
  boolean active;
  
  shoot(float xPos, float yPos, float a){
    this.xPos = xPos;
    this.yPos = yPos;
    this.a = a/2;
    this.active = true;
    
    for(int i = 1; i < 5; i++){
      chains.add(loadImage("assets/aliens/chain"+i+".png"));
    }
  }
  
  void drawShoot(){
    if (stage == 4){
      stage = 0;
    }
    if (active){
      image(chains.get(stage),xPos,yPos,chains.get(stage).width,chains.get(stage).height);
      stage++;
      xPos-=a;
      yPos+=7.5;
    }
    if(xPos < -width || yPos > height){
      active = false;
    }
  }
  
  
}
