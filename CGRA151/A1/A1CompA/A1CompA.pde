// setup
background(120);
size(500,600);
fill(255);
// defines variables
float boxWidth = 0;
float boxHeight = height/20;
final float SPACE = 5;
// Y loop
for(float ySquare=0;ySquare<height;ySquare+=boxHeight){
  // X loop
  for(float xSquare=0;xSquare<width;xSquare+=boxWidth+SPACE){
    // Sets a random box size and draws it
    boxWidth = random(0,30);
    rect(xSquare,ySquare,boxWidth,boxHeight);
  }
}
