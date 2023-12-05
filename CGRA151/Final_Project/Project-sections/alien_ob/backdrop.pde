class backDrop{
  PImage image;
  float xPos,yPos;
  String img;
  ArrayList<PImage> images = new ArrayList<PImage>();
  float a = 0;
  float layer;
  
  
  backDrop(String s, float y,float index, float l){
    if (s.contains("mount")){
      image = loadImage("assets/back-mountains.png");
    }
    if (s.contains("hill")){
      image = loadImage("assets/front-hills.png");
    }
    yPos = y;
    xPos = index*width-width;
    layer = l;
  }
  
  void accelerate(float t){
    a+=t;
  }
  
  void drawBack(){
    if(xPos < -width){
      xPos = width + a*layer;
    }
    if(xPos > 2*width){
      xPos = -width + a*layer;
    }
    image(image,xPos,yPos,width,height/2);
    xPos += layer*a;
  }
  
}
