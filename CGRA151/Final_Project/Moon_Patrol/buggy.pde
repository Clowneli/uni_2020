class buggy {
  float xPos = 100;
  float yPos = 275;
  boolean active = true;
  PImage sprite = loadImage("assets/buggy.png");
  float gravity = 10;
  boolean riseFall = false; //false when falling
  float jumpHeight = 120;
  
  //draw buggy
  void drawBuggy(){
    image(sprite,xPos,yPos,100,75);
    drawWheels();
  }
  
  //Draws the wheels
  void drawWheels(){
    fill(0);
    ellipse(xPos+20,yPos+70,28,28);
    ellipse(xPos+50,yPos+70,28,28);
    ellipse(xPos+80,yPos+70,28,28);
    
  }
  
  //jump method
  void jump(){
    if (yPos == 275){
      riseFall = true;
    }
  }
  
  //gravity function
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
  }
  
  //method to detect if the buggy touches a rock
  public boolean touchingRock(natural_ob rock){
    //xValue getters
    if ((this.xPos <= rock.getX()) && (this.xPos+100 >= rock.getX()+rock.getWidth())){
      if(this.yPos+rock.getHeight() > rock.getY()){return true;}
    }
    return false;
  }
  
  //Expload animation
  void animateExploade(){
    
    PImage img = loadImage("assets/exploade/exp4.png");
    image(img,xPos,yPos,img.width*3,img.height*3);
    
    delay(1000);
  }
}

//class for the fire method
public class fire{
  float xPos,yPos;
  PImage image = loadImage("assets/fire.png");
  boolean active;
  
  //constructor
  fire(float x, float y){
    this.xPos= x;
    this.yPos=y;
    active=true;
  }
  
  //draw method
  void drawFire(){
    if (active){
      image(image,xPos,yPos-image.height*2);
      xPos+=20;
      if(xPos > width+image.width){
        active = false;
      }
    }
  }
  
  //method to detect if the bullet touches a rock
  public boolean touchingRock(natural_ob rock){
    //xValue getters
    if(active){
      if ((this.xPos <= rock.getX()) && (this.xPos+100 >= rock.getX()+rock.getWidth())){
        if(this.yPos+rock.getHeight() > rock.getY()){active=false; return true;}
      }
    }
    return false;
  }
  
}
