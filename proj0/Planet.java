

public class Planet{
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  public static final double G = 6.67e-11;


  public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
                xxPos = xP;
                yyPos = yP;
                xxVel = xV;
                yyVel = yV;
                mass = m;
                imgFileName = img;
              }
  public Planet(Planet p){
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;

  }


  public double calcDistance(Planet p1){
    double distance;
    distance = Math.sqrt(Math.pow(p1.xxPos - this.xxPos,2) + Math.pow(p1.yyPos - this.yyPos,2));
    return distance;
  }

  public double calcForceExertedBy(Planet p1){
    double force;
    force = G * p1.mass * this.mass/Math.pow(this.calcDistance(p1), 2);
    return force;
  }


  public double calcForceExertedByX(Planet p1){
    double forceX;
    forceX = (p1.xxPos -this.xxPos)/this.calcDistance(p1) * this.calcForceExertedBy(p1);
    return forceX;
  }


  public double calcForceExertedByY(Planet p1){
    double forceY;
    forceY = (p1.yyPos -this.yyPos)/this.calcDistance(p1) * this.calcForceExertedBy(p1);
    return forceY;
  }


  public double calcNetForceExertedByX(Planet[] allPlanets){
    double NetforceX = 0;
    for(int i = 0; i < allPlanets.length; i++){
      if (! this.equals(allPlanets[i])){
        NetforceX = this.calcForceExertedByX(allPlanets[i]) + NetforceX;
      }
    }
    return NetforceX;
  }


  public double calcNetForceExertedByY(Planet[] allPlanets){
    double NetforceY= 0;
    for(int i = 0; i < allPlanets.length; i++){
      if (! this.equals(allPlanets[i])){
        NetforceY = this.calcForceExertedByY(allPlanets[i]) + NetforceY;
      }
    }
    return NetforceY;
  }


  public void update(double dt, double fX, double fY){
    double aX = 0;
    double aY = 0;
    aX = fX/this.mass;
    aY = fY/this.mass;
    this.xxVel = this.xxVel + aX * dt;
    this.yyVel = this.yyVel + aY * dt;
    this.xxPos = this.xxPos + this.xxVel * dt;
    this.yyPos = this.yyPos + this.yyVel * dt;

  }
  public void draw(){
    String img = "images/" + this.imgFileName;
    StdDraw.picture(xxPos, yyPos, img);

  }

}
