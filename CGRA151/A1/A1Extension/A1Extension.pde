//initalises constants and arrays
ArrayList<star> stars;
ArrayList<shootingStar> shootingStars;
final int MAXSTARS = int(random(50,150));
final int STARSIZE = 5;

//code setup
void setup(){
  size(800,600);
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

//draws the night sky and mountains
void draw(){  
  background(27,30,35);
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
  
  //mountains
  drawMountains();
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

//method for drawing mountains
void drawMountains(){
  fill(0,35,0);
  stroke(0);
  ellipse(width*2/3,height,width,height/2);
  ellipse(width/3,height,width,height/3);
}

//Class for stars
class star{
  //variables for each star
  float x = random(0,width);
  float y = random(0,height);
  float alpha;
  int timer = int(random(0,100));
  
  //lighter value for the stars
  void lighter(){
    alpha = 255-(timer*2.55);
    fill(255, 255, 243, alpha);
  }
  //darker values for the stars
  void darker(){
    alpha = ((100-timer)*2.55);
    fill(255, 255, 243, alpha);
  }   
  //Code to remake a star
  void restartStar(){
    this.x = random(0,width);
    this.y = random(0,height);
    this.timer = 0;
  }
  //Draws the star and determins if it should darken, lighten, or be remade
  void drawStar(){
    if (timer <= 50){
      this.lighter();
      ellipse(this.x,this.y,STARSIZE,STARSIZE);
    }
    else if (timer < 100){
      this.darker();
      ellipse(this.x,this.y,STARSIZE,STARSIZE);
    }
    else{this.restartStar();}
    this.timer++;
  }
}

//Class for drawing the shooting stars
class shootingStar{
  //sets variables for each star
  float x = random(width/5,width*3/5);
  float y = 0;
  int timeTill = int(random(300,350));
  int timer = 0;
  int tailNum;
  
  //animation for shooting star
  void shoot(){
    if(timer>=timeTill){
      fill(255,255,243,this.tailAlpha());
      ellipse(this.x-7.5*this.tailNum,this.y-7.5*this.tailNum,STARSIZE,STARSIZE);
      this.travel();
    }
    timer++;
    if((this.y > height)||(this.x > width)){
      this.reset();
    }
  }
  void setNumber(int x){
    this.tailNum = x;
  }
  
  //goves the alpha value for each part of the shooting star
  float tailAlpha(){
    return 255;
  }
  //Makes the SS move
  void travel(){
    this.x+=width/20;
    this.y+=height/30;
  }
  //resets the star when past the mountains
  void reset(){
    this.timer=0;
    this.x = random(width/5,width*3/5);
    this.y = 0;
    this.timeTill = int(random(200,300));
    this.timer = 0;
  }
}
