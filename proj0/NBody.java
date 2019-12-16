public class NBody{

  public static double readRadius(String filename){
    In in = new In(filename);

    int Planet_num = in.readInt();
    double Universe_Radius = in.readDouble();
    return Universe_Radius;
  }

  public static Planet[] readPlanets(String filename){
    In in = new In(filename);

    int Planet_num = in.readInt();
    double Universe_Radius = in.readDouble();
    Planet [] all_planets = new Planet[Planet_num];
    for(int i = 0; i < all_planets.length; i++){
      double xxPos = in.readDouble();
      double yyPos = in.readDouble();
      double xxVel = in.readDouble();
      double yyVel = in.readDouble();
      double mass = in.readDouble();
      String imgFileName = in.readString();
      all_planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);

    }
    return all_planets;

  }
  public static void main(String[] args){
    double T = Double.parseDouble(args[0]);
    double dt =Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);

    Planet [] all_planets = readPlanets(filename);

    String background = "images/starfield.jpg";
    StdDraw.setScale(-radius, radius);
    StdDraw.clear();
    StdDraw.picture(0, 0, background);

    // StdDraw.show();
    // StdDraw.pause(10);


    for(int i = 0; i < all_planets.length; i++){
      all_planets[i].draw();
    }
    StdDraw.enableDoubleBuffering();
    double t = 0;
    while(t < T){
      double [] xForces = new double[all_planets.length];
      double [] yForces = new double[all_planets.length];
      for (int i = 0; i < all_planets.length; i++){
        xForces[i] = all_planets[i].calcNetForceExertedByX(all_planets);
        yForces[i] = all_planets[i].calcNetForceExertedByY(all_planets);
        all_planets[i].update(dt, xForces[i], yForces[i]);
      }
      StdDraw.setScale(-radius, radius);
      StdDraw.clear();
      StdDraw.picture(0, 0, background);

      for(int i = 0; i < all_planets.length; i++){
        all_planets[i].draw();
      }
      StdDraw.show();
      StdDraw.pause(10);

      t = t + dt;
    }

  }
  StdOut.printf("%d\n", planets.length);
  StdOut.printf("%.2e\n", radius);
  for (int i = 0; i < planets.length; i++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
}
}
