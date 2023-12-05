//core constructors
ArrayList<natural_ob> rocks = new ArrayList<natural_ob>();
buggy buggy;
ground ground;
ArrayList<backdrop> backdrops = new ArrayList<backdrop>();
boolean running = true;
stage stage;
ArrayList<Alien_ob> aliens = new ArrayList<Alien_ob>();
ArrayList<fire> firing = new ArrayList<fire>();
int timer = 25;
mainMenu menu;
ArrayList<star> stars;
final int MAXSTARS = int(random(50,150));
final int STARSIZE = 5;


//setup
void setup(){
  
  menu = new mainMenu();

  //basic function
  size(1000,500);
  frameRate(25);
  //defines objects
  ground = new ground();
  buggy = new buggy();
  //buggy.makeWheels();
  stage = new stage(1);

  backdrops.add(new backdrop("mount",50,0,0.5));
  backdrops.add(new backdrop("mount",50,1,0.5));
  backdrops.add(new backdrop("mount",50,2,0.5));
  backdrops.add(new backdrop("hill",125,0,1));
  backdrops.add(new backdrop("hill",125,1,1));
  backdrops.add(new backdrop("hill",125,2,1));
  
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));
  
  stars=new ArrayList<star>();
  for (int i=0;i<MAXSTARS;i++){
    star tempStar = new star();
    stars.add(tempStar);
  }

}

/**
** main running method
*/
void draw(){
  
  boolean hit = false;
  if(menu.menuStage < 3){
    menu.draw();
  }
  else{
    keychecks();
    timer--;
    background(0);
    for(star star : stars){
      noStroke();
      star.drawStar();
    }
    for(backdrop back:backdrops){
      back.drawBack();
    }
    ground.drawGround();
    buggy.gravity();
    buggy.drawBuggy();
    for(natural_ob rock:rocks){
      rock.drawOb();
      if(buggy.touchingRock(rock)){
        hit=true;
      }
    }
   for(fire fire:firing){
      if(fire!=null){
        fire.drawFire();
        for(natural_ob rock:rocks){
          if(fire.touchingRock(rock)){
            rock.hitRock();
          }
        }
      }
    }
    
    stage.drawStage();
    completedLevel();
    for(Alien_ob alien:aliens){
      alien.drawOb();
      if(alien.hasHit(buggy)){
        hit=true;
      }
    }
    if(hit){ 
      buggy.animateExploade(); 
      restart();
    }
  }
}




//restart method
public void restart(){
  ground = new ground();
  buggy = new buggy();
  aliens = new ArrayList<Alien_ob>();
  stage = new stage(stage.getStage());
  firing = new ArrayList<fire>();

  backdrops.clear();
  backdrops.add(new backdrop("mount",50,0,0.5));
  backdrops.add(new backdrop("mount",50,1,0.5));
  backdrops.add(new backdrop("mount",50,2,0.5));
  if (stage.getStage()==1){
    backdrops.add(new backdrop("hill",125,0,1));
    backdrops.add(new backdrop("hill",125,1,1));
    backdrops.add(new backdrop("hill",125,2,1));
  }
  else{
    backdrops.add(new backdrop("alien",125,0,1));
    backdrops.add(new backdrop("alien",125,1,1));
    backdrops.add(new backdrop("alien",125,2,1));
    
    aliens.add(new Alien_ob(random(1000,3000),int(random(1,4))));
    aliens.add(new Alien_ob(random(1000,3000),int(random(1,4))));
    aliens.add(new Alien_ob(random(1000,3000),int(random(1,4))));
  }
  
  rocks.clear();
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"red"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));
  rocks.add(new natural_ob(random(1000,5000),"rock"));

}

//method for completing stage
void completedLevel(){
  PFont f = createFont("assets/irem-font.ttf",32);
  textFont(f,64);
  textAlign(CENTER);
  fill(255);
  
  if (stage.isComplete()){
    if(stage.getStage() == 2){
      text("Level 2 Complete, YOU WIN!",width/2,height/3);
      noLoop();
    }
    else{
      text("Level 1 Complete",width/2,height/3);
      textFont(f,32);
      text("Press 'n' to continue",width/2,height*2/3);
      noLoop();
    }
  }
}

//keybinds
public void keyPressed(){
  if(key==' '){
    menu.menuStage++;
    if (menu.menuStage < 3) {delay(1000);}
  }
  
  //p is the pause button (will also pull up controls)
  if (key == 'p'){
    running = !running;
      if (!running) {
      noLoop();
    } else {
      loop();
    }
  }
  //restart button
  if (key == 'r'){
    delay(1000);
    restart(); 
  }
  //next stage button
  if (key == 'n'){
    loop();
    delay(1000);
    if(stage.getStage()==1){
      stage = new stage(2);
    }
    else if(stage.getStage()==2){
      stage = new stage(1);
    }
    restart();
    }
}
//needed to seperate as one paused the entire program and one runs the programs functions
void keychecks(){
  if(keyPressed){
    
    if (running){
      //makes buggy jump
      if (key == 'w'){
        buggy.jump();
      }
      //lets background move and rocks move to give illusion of traveling
      if (key == 'a'){
        //moves satge
        stage.accelerate(-1);
        //moves background
        for(backdrop back: backdrops){
          back.accelerate(1);
        }
        //moves the rocks
        for(natural_ob rock:rocks){
          rock.accelerate(1);
        }
        //moves the aliens
        for(Alien_ob alien:aliens){
          alien.accelerate(-1);
        }
      }
      if (key == 'd'){
        //moves stage
        stage.accelerate(1);
        //moves background
        for(backdrop back: backdrops){
          back.accelerate(-1);
        }
        //moves the rocks
        for(natural_ob rock:rocks){
          rock.accelerate(-1);
        }
        //moves the alien
        for(Alien_ob alien:aliens){
          alien.accelerate(1);
        }
      }
      if(key == ' ' && timer < 0){
        firing.add(new fire(buggy.xPos,buggy.yPos+70));
        timer = 25;
      }
    }
  }
}
