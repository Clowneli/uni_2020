// setup
size(600,600);
background(120);
fill(255);
// for loop for code
for (int i =0; i < 100; i++){
    float triSizeX = random(20,150);
    float triSizeY = random(20,300);
    // variable for triangle
    float x1 = random(0,width);
    float y1 = random(0,height);
    float x2 = x1+random(-1.5,1.5)*triSizeX;
    float y2 = y1+random(-1.5,1.5)*triSizeY;
    float x3 = random(x1,x2);
    float y3 = random(y1,y2);
    // triangle
    triangle(x1,y1,x2,y2,x3,y3);
}
