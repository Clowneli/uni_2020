stages stage;

void settings(){
  size(1000,500);
}
void setup(){
  //
  //size(1000,500);
  //
  frameRate(25);
  
  stage = new stages(1);
 
  
}

void draw(){
  background(255);
  keychecks();
  stage.drawStage();
  completedLevel();
}

void completedLevel(){
  if (stage.isComplete()){
    delay(1000);
    stage = new stages(2);
  }
}

void keychecks(){
  if(keyPressed){
    if (key == 'a'){
      stage.accelerate(-1);
    }
    if (key == 'd'){
      stage.accelerate(1);
    }
  }
}
