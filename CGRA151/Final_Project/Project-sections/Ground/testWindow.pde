ground ground;
  ArrayList<rocks> rock = new ArrayList<rocks>();

public void settings() {
  size(1000, 500);
}
void setup(){
  //
  frameRate(25);
  //
  ground = new ground();
  for (int i = 0; i > 25; i++){
      rock.add(new rocks(random(0,width),random(200,height),random(15,50)));    
    }
}

void draw(){
  if (keyPressed){
    if (key == 'a'){
      for (rocks roks: rock){
      roks.accelerate(-1);
    }
    }
    if (key == 'd'){
      for (rocks roks: rock){
      roks.accelerate(1);
    }
    }
  }
  background(255);
  ground.drawGround();
  for (rocks roks: rock){
    
      roks.travel();
      roks.drawRocks();
    }
}
