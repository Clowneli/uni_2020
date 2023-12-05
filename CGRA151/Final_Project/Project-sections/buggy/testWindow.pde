//core constructors
Buggy buggy;
//setup
void settings(){
  size(1000,500);
}
void setup(){
  //
  //size(1000,500);
  //
  frameRate(25);
  
  buggy = new Buggy();
  
}

void draw(){
  background(255);
  
  keychecks();
  
  buggy.gravity();
  buggy.drawBuggy();
  
}

void keychecks(){
  if(keyPressed){
    if (key == 'w'){
      buggy.jump();
    }
    if (key == 'a'){
      buggy.accelerate(-1);
    }
    if (key == 'd'){
      buggy.accelerate(1);
    }
  }
}
