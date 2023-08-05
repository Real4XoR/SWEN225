package main;

import java.awt.Dimension;
import java.awt.Graphics;

import imgs.Img;

class Sword extends ControllableDirection implements Entity{
  private Entity wielder;
  private double weaponRadiant = 0;
  public double distance(){ return 0.8d; }
  public double speed(){ return 0.2d; }
  Sword(Entity wielder){ this.wielder=wielder; }

  public Point location(){ 
    var dir = new Point(Math.sin(weaponRadiant),Math.cos(weaponRadiant));
    return dir.times(distance()).add(wielder.location());
  }
  public void onHit(Model m, Entity e){ if(e instanceof Monster){ ((Monster)e).kill(); }}
  public double effectRange(){ return 0.3d; }
  public void ping(Model m){
    weaponRadiant+=direction().arrow(speed()).x();
    weaponRadiant%=Math.PI*2d;
    var l = this.location();
    m.entities().stream()
      .filter(e->e!=this)
      .filter(e->e.location().distance(l).size()<effectRange())
      .forEach(e->onHit(m,e));
  }
  public void draw(Graphics g, Point center, Dimension size) {
    drawImg(Img.Sword.image, g, center, size);
  }
}