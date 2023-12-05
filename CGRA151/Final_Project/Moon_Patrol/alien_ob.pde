public class Alien_ob{
  float xPos;
  float size;
  PImage image;
  float yPos;
  float a = 1;
  int alienType;
  float wait;
  float timer;
  ArrayList<shoot> shot = new ArrayList<shoot>();
  
  //constructor method
  Alien_ob(float x,int type){
    image = loadImage("assets/aliens/alien"+type+".png");
    this.wait = x;
    this.alienType = type;
    yPos = random(50,200);
    xPos = -image.width*2;
    timer = 95;
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
      wait = random(2000,4000);
    }
    wait -= abs(a);
    if(timer==0){
        shot.add(new shoot(xPos,yPos));
        timer = 65;
      }
      if (shot != null){
        for(shoot shot:shot){
          shot.drawShoot(a);
        }
      }
  }
  
  //Accelerate method (moves alien with frame)
  void accelerate(float t){    
    a+=t;
    if(a<1){a=1;}  
    if(a>6){a=6;}
  }


  public boolean hasHit(buggy buggy){
    for(shoot shot:shot){
      if(shot != null){
        if ((buggy.xPos <= shot.getX()) && (buggy.xPos+100 >= shot.getX()+shot.getWidth())){
          if(buggy.yPos+shot.getHeight() > shot.getY()){return true;}
        }
      }
    }
    return false;
  }
}





public class shoot{
  float xPos,yPos;
  int stage = 0;
  ArrayList<PImage> chains =  new ArrayList<PImage>();
  boolean active;
  
  //constructor method
  shoot(float xPos, float yPos){
    this.xPos = xPos;
    this.yPos = yPos;
    this.active = true;
    
    for(int i = 1; i < 5; i++){
      chains.add(loadImage("assets/aliens/chain"+i+".png"));
    }
  }
  
  //draw method
  void drawShoot(float a){
    
    if (active){
      image(chains.get(stage),xPos,yPos,chains.get(stage).width,chains.get(stage).height);
      stage++;
      
      if(yPos < 300){
        yPos+=5;
      }
      else{xPos-=20;}
    }
    if(xPos < -width || yPos > height){
      active = false;
    }
    if (stage == 4){
      stage = 0;
    }
  }
  
  //getter methods
  public float getX(){return xPos;}
  public float getY(){return yPos-chains.get(stage).height;}
  public float getWidth(){return chains.get(stage).width;}
  public float getHeight(){return chains.get(stage).height;}
  public boolean isActive(){return active;}

  
}
