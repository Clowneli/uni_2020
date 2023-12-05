class backdrop{
  PImage image;
  float xPos,yPos;
  String img;
  ArrayList<PImage> images = new ArrayList<PImage>();
  float a = 0;
  float layer;
  
  //constructor
  backdrop(String s, float y,float index, float l){
    if (s.contains("mount")){
      image = loadImage("assets/back-mountains.png");
    }
    if (s.contains("hill")){
      image = loadImage("assets/front-hills.png");
    }
    if (s.contains("alien")){
      image = loadImage("assets/front-city.png");
    }
    yPos = y;
    xPos = index*width-width;
    layer = l;
  }
  
  //accelerate
  void accelerate(float t){
    a+=t;
    if(a > 0){
      a=0;
    }
    if (a > -6){
      a=-6;
    }
  }
  
  //draw the background
  void drawBack(){
    if(xPos < -width- layer*3){
      xPos = width;
    }
    if(xPos < width*2){
      image(image,xPos,yPos,width,height/2);
    }
    xPos += layer*a;
  }
  
}
