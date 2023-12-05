PFont f;
int menuStage = 1; 

void setup(){
  size(1000,500);
  f = createFont("assets/irem-font.ttf",32);
  background(27,30,35);
  frameRate(25);
  //initialises stars as an array list
  stars=new ArrayList<star>();
  for (int i=0;i<MAXSTARS;i++){
    star tempStar = new star();
    stars.add(tempStar);
  }
  //initialises shooting star and tail as an array list.
  shootingStars=new ArrayList<shootingStar>();
  shootingStar tempSStar = new shootingStar();
  shootingStars.add(tempSStar);
}


void draw(){
  background(27,30,35);
  drawStars();
  if(menuStage == 1){
    textFont(f,64);
    fill(255);
    textAlign(CENTER);
    text("Moon Patrol",width/2,height/3);
    textFont(f,32);
    text("Press SPACE to start",width/2,height*2/3);
  }
  if(menuStage == 2){
    textFont(f,32);
    fill(255);
    textAlign(CENTER);
    text("CONTROLS",width/2,height/5);
    textFont(f,26);
    text("Use 'w', 'a' and 'd' to move the buggy",width/2,height*2/5);
    text("Use 'SPACE' to shoot the brown rocks",width/2,height*3/6);
    text("The aliens and red rocks are too tough",width/2,height*4/7);
    text("to shoot and need to be jumped over",width/2,height*5/8);
    text("Use P,R or n to pause,",width/2,height*5/7);
    text("reset or change stage",width/2,height*7/9);
    textFont(f,32);
    text("Press SPACE to start",width/2,height*8/9);
  }
  if(menuStage<=3){

  }
}


void keyPressed(){
  if(key==' '){
    menuStage++;
    delay(1000);
  }
}
