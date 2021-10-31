package model;

import java.util.ArrayList;

/**This represents a dungeon which is where the player navigates from a start point to an end point.
 *
 */
public interface Dungeon {

  Point2D getStartPoint();

  Point2D getEndPoint();

  ArrayList<Integer> getAllCaves();

  ArrayList<String> getMovesAtCaveIndex(Point2D inputCavePoint);

  ArrayList<Treasure> returnCaveTreasure(Point2D inputCavePoint);

  boolean isMoveValid(Point2D inputCavePoint, String direction);

  boolean gameFinished(Point2D inputCavePoint);

}