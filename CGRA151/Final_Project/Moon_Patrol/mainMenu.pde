public class mainMenu{
PFont f;
int menuStage = 1; 
ArrayList<star> stars;
ArrayList<shootingStar> shootingStars;
final int MAXSTARS = int(random(50,150));
final int STARSIZE = 5;

//constructor class
mainMenu(){
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

//draws the mainMenu
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
  
}


//draws the night sky and mountains
void drawStars(){
  //stars
  for(star star : stars){
    noStroke();
    star.drawStar();
  }
  
  //shooting star
  int count = 0;
  for(shootingStar shootingStar : shootingStars){
    noStroke();
    shootingStar.setNumber(count);
    shootingStar.shoot();
    count++;
  }
  
  //moon
  drawMoon();

}

void drawMoon(){
  //defines the variables the moon interacts with
  noStroke();
  float moonShine = 0;
  int moonSize = 200;
  for(int i=0; i<45; i++){
    //draws the moon and the moons shine
    fill(161,173,181,moonShine);
    ellipse(width*7/8,height/8,moonSize,moonSize);    
    moonShine+=5;
    moonSize-=5;
  }
  fill(218,228,235);
  ellipse(width*7/8,height/8,100,100); 
  //draws the moon craters
  fill(188,199,207);
  ellipse(width*7/8-10,height/8-20,15,15);
  ellipse(width*7/8+10,height/8+20,10,10);
  ellipse(width*7/8-10,height/8+10,10,10);
  ellipse(width*7/8+10,height/8-20,20,20);
  ellipse(width*7/8-20,height/8+20,15,15);
}

}
