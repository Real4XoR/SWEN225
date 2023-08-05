package main;

import java.awt.event.KeyEvent;

class Controller extends Keys{
  Controller(Camera c,Sword s){
    setAction(Compact.keys.get(0),c.set(Direction::up),c.set(Direction::unUp));
    setAction(Compact.keys.get(1),c.set(Direction::down),c.set(Direction::unDown));
    setAction(Compact.keys.get(2),c.set(Direction::left),c.set(Direction::unLeft));
    setAction(Compact.keys.get(3),c.set(Direction::right),c.set(Direction::unRight));
    setAction(Compact.keys.get(4),s.set(Direction::left),s.set(Direction::unLeft));
    setAction(Compact.keys.get(5),s.set(Direction::right),s.set(Direction::unRight));
  }
}