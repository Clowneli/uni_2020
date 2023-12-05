class ground{
  float xPos,yPos;
  color ground = color(192,96,0);
  ArrayList<rocks> rock = new ArrayList<rocks>();
  
  void makeRocks(){
    for (int i = 0; i > 25; i++){
      rock.add(new rocks(random(0,width),random(400,height),random(15,50)));    
    }
  }
  
  void drawGround(){
    noStroke();
    fill(ground);
    rect(0,350,width,height/3);
    for (rocks roks: rock){
      roks.drawRocks();
    }
  }
  void accelerate(float a){
    for (rocks roks: rock){
      roks.accelerate(a);
    }
  }
  
}
