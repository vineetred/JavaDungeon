package driver;

import model.Dungeon;
import model.DungeonImpl;

public class Driver {

  public static void main(String[] args) {
    System.out.print("\nCreating a dungeon!");
    Dungeon test = new DungeonImpl(false, 5, 5, 0, 20);
    System.out.println("\nStart point: " + test.getStartPoint());
    System.out.println("\nEnd point: " + test.getEndPoint());
    System.out.println("\nAll caves: " + test.getAllCaves());

  }
}
