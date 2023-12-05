// setup
void setup(){
  background(120);
  size(500,600);
  frameRate(20);
}

// draw function
void draw(){
  if(mousePressed){fill(255,0,0);}
  else{fill(0,0,255);}
  ellipse(mouseX,mouseY,50,50);
}
