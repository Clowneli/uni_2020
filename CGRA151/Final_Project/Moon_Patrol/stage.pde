class stage{
  float xPos = width/4;
  float yPos = 25;
  float w = width/2;
  float h = 25;
  float p,b;
  float a = 0;
  int stage;
  
  color borderCol = color(55);
  color backCol;
  color bar;
  
  //constructor
  stage(int stageNo){
    if(stageNo==1){
      backCol = color(55);
      bar = color(255,0,0);
      p=500/(7*35*width);
      b=50;
    }
    
    if(stageNo==2){
      backCol = color(255,0,0);
      bar = color(0,255,0);
      p=500/(7*45*width);
      b=60;
    }
    this.stage = stageNo;
  }
  
  //draw method
  void drawStage(){
    //base
    fill(borderCol);
    stroke(borderCol);
    rect(xPos,yPos,w,h);
    //bottom layer (only for stage 2)
    fill(backCol);
    stroke(backCol);
    rect(xPos+5,yPos+5,w-10,h-10);
    //top layer
    fill(bar);
    stroke(bar);
    rect(xPos+5,yPos+5,p,h-10);
    p+=a;
  }
  
  //accerate method
  void accelerate(float t){
    a+=t/b;
    if(a < 0){
      a=0;
    }
  }
  
  //checks if stage is complete
  public boolean isComplete(){
    if (p >= w-10){
      return true;
    }
    return false;
  }
  
  //getter methods
  public int getStage(){return stage;}
}
