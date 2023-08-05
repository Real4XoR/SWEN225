package main;

import java.util.List;
import javax.swing.JFrame;

record Phase(Model model, Controller controller){
  /**
   * @param next Runnable
   * @param first Runnable
   * @param level Int
   * @return Phase
   */
  static Phase level(Runnable next, Runnable first, int level) {
    JFrame f = new JFrame();
    Camera c = new Camera(new Point(5,5));
    Sword s = new Sword(c);
    Cells cells = new Cells();
    var m = new Model(){
      List<Entity> entities;
      public Camera camera() { return c; }
      public List<Entity> entities() { return entities; }
      public void remove(Entity e){
        entities = entities.stream()
          .filter(ei->!ei.equals(e))
          .toList();
      }
      public Cells cells(){ return cells; }
      public void onGameOver(){ first.run(); }
      public void onNextLevel(){ next.run(); }
    };

    switch (level) {
      case 1:
        m.entities = List.of(c, s, new Monster(new Point(0, 0)));
        break;
      case 2:
        m.entities = List.of(c, s, new Monster(new Point(0, 0)), new Monster(new Point(0, 16)), new Monster(new Point(16, 0)), new roamingMonster(new Point(16, 16)));
        break;
      case 3:
        m.entities = List.of(c, s, new Sword(new Monster(new Point(0, 0))));
        break;
      case 4:
        javax.swing.JOptionPane.showMessageDialog(f, "Winner winner chicken dinner!", "You Win", javax.swing.JOptionPane.PLAIN_MESSAGE);
    }
    return new Phase(m,new Controller(c,s));
  }
}