//core constructors
ArrayList<backDrop> backdrops = new ArrayList<backDrop>();
ArrayList<natural_ob> rocks = new ArrayList<natural_ob>();


void settings(){
  size(1000,500);

}
//setup
void setup(){
  //
    frameRate(25);
  //
  
  backdrops.add(new backDrop("mount",0,0,2));
  backdrops.add(new backDrop("mount",0,1,2));
  backdrops.add(new backDrop("mount",0,2,2));
  backdrops.add(new backDrop("hill",125,0,1));
  backdrops.add(new backDrop("hill",125,1,1));
  backdrops.add(new backDrop("hill",125,2,1));
  rocks.add(new natural_ob(random(1000,5000)));
  rocks.add(new natural_ob(random(1000,5000)));
  rocks.add(new natural_ob(random(1000,5000)));
  rocks.add(new natural_ob(random(1000,5000)));

}
void draw(){
  background(255);
  keychecks();
  for(backDrop back:backdrops){
    back.drawBack();
  }
  for(natural_ob rock:rocks){
    rock.drawOb();
  }
}
void keychecks(){
  if(keyPressed){
    for(natural_ob rock:rocks){
      if (key == 'a'){
          rock.accelerate(1);
        }
        if (key == 'd'){
          rock.accelerate(-1);
        }
    }
    for(backDrop back:backdrops){
      if (key == 'a'){
        back.accelerate(1);
      }
      if (key == 'd'){
        back.accelerate(-1);
      }
    }
  }
}
