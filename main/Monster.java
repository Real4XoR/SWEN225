package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import imgs.Img;

class Monster implements Entity {
  protected Point location;
  public Point location(){ return location; }
  public void location(Point p){ location=p; }
  Monster(Point location){ this.location=location; }
  public double speed(){ return 0.05d; }
  private String state = "asleep";
  public int deadPing = 0;
  public void kill() { state = "dead"; }
  public void ping(Model m) {
    var arrow = m.camera().location().distance(location);
    double size = arrow.size();
    if (state.equals("dead")) {
      if (deadPing == 100) {m.remove(this); }
      deadPing += 1;
    } else if (size < 6) {
      state = "awake";
      arrow = arrow.times(speed() / size);
      location = location.add(arrow);
      if (size < 0.06d) {m.onGameOver();}
    } else { state = "asleep"; }
  }

  public double chaseTarget(Monster outer, Point target){
    var arrow = target.distance(outer.location());
    double size = arrow.size();
    arrow = arrow.times(speed()/size);
    outer.location(outer.location().add(arrow));
    return size;
  }
  public void draw(Graphics g, Point center, Dimension size) {
    if (state.equals("awake")) { drawImg(Img.AwakeMonster.image, g, center, size);}
    else if (state.equals("asleep")) { drawImg(Img.SleepMonster.image, g, center, size);}
    else if (state.equals("dead")) { drawImg(Img.DeadMonster.image, g, center, size);}
  }
}
class roamingMonster extends Monster implements Entity {
  roamingMonster(Point location) {
    super(location); }
  public void ping(Model m) {
    Random rand = new Random();
    double randomXPoint = rand.nextDouble() * 16;
    double randomYPoint = rand.nextDouble() * 16;
  }
}
class bossMonster extends Monster implements Entity {
  bossMonster(Point location) {
    super(location);
  }

}