ArrayList<CRS> curves = new ArrayList<CRS>();
ArrayList<car> cars = new ArrayList<car>();
//adds the pointer
pointer point = new pointer();


void setup(){
  size(1000,1000);
  frameRate(25);
  //adds the curves
  curves.add(new CRS(40, 104, 20, 104, 800, 96, 104, 20,1));
  curves.add(new CRS(20, 104, 800, 96, 492, 800, 360, 160,2));
  curves.add(new CRS(160, 360, 492, 800, 360, 360, 460, 260,3));
  curves.add(new CRS(260, 460, 360, 360, 860, 160, 160, 860,4));
  curves.add(new CRS(160, 860, 860, 160, 160, 860, 360, 560,5));
  curves.add(new CRS(360, 560, 160, 860, 20, 104, 0, 104,6));
  
  //adds the cars
  cars.add(new car(1, curves, point));
  cars.add(new car(2, curves, point));
  cars.add(new car(3, curves, point));
  cars.add(new car(4, curves, point));
  cars.add(new car(5, curves, point));
  cars.add(new car(6, curves, point));
  
  
  
}
void draw(){
  clear();
  background(255);
  noFill();
  //draws each 
  for(CRS curve : curves){
    curve.drawCurve();
  }
  point.drawPointer(mouseX,mouseY);
  for(car car : cars){
    car.drawCar();
  }
}
