package model;

import java.util.ArrayList;

/**This represents a dungeon which is where the player navigates from a start point to an end point.
 *
 */
public interface Dungeon {
  int getStartPoint();

  int getEndPoint();

  ArrayList<Integer> getAllCaves();

  ArrayList<Integer> getMovesAtCaveIndex(int caveIndex);

  ArrayList<Treasure> returnCaveTreasure(int caveIndex);

}