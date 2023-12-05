class rocks{
  float xPos,yPos;
  float diam;
  color inside = 155; //color(144,72,0);
  color outside = 250; //color(72,36,0);
  float a = 0;
  
  rocks(float xpos, float ypos, float Diam){
    this.xPos = xpos;
    this.yPos = ypos;
    this.diam = Diam;
  }
  
  void drawRocks(){
    fill(outside);
    ellipse(xPos,yPos,diam,diam);
    fill(inside);
    ellipse(xPos,yPos,diam*2/3,diam*2/3);
  }
  
  void travel(){
    this.xPos += a;
    if (xPos < 0){xPos = width;}
  }
  
  void accelerate(float t){
    a+=t;
  }
}
