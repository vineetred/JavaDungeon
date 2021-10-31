package test;

import model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class dungeonTest {

  // Helper methods

  private static void playerPickTreasureFromCave(Player testPlayer,
                                                 ArrayList<Treasure> caveTreasure) {
    if (caveTreasure == null) {
    }
    else {
      testPlayer.pickUpTreasure(caveTreasure);
    }
  }

  // Dungeon Exceptions
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRowsAndColumns() {
    Dungeon testDungeon = new DungeonImpl(false, -1, -1, 0, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFewerThanSixNodes() {
    Dungeon testDungeon = new DungeonImpl(false, 1, 4, 0, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFewerThanThreeColumns() {
    Dungeon testDungeon = new DungeonImpl(false, 2, 2, 0, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanTwentyTreasure() {
    Dungeon testDungeon = new DungeonImpl(false, 6, 9, 0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInterconnectivity() {
    Dungeon testDungeon = new DungeonImpl(false, 6, 9, -1, 40);
  }

  @Test
  public void testGetStartPointDungeon() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    assertEquals(0, testDungeon.getStartPoint().getRow());
    assertEquals(3, testDungeon.getStartPoint().getColumn());

    // With non-random wrapping dungeon
    testDungeon = new DungeonImpl(true);
    assertEquals(0, testDungeon.getStartPoint().getRow());
    assertEquals(3, testDungeon.getStartPoint().getColumn());
  }

  @Test
  public void testGetEndPoint() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    assertEquals(3, testDungeon.getEndPoint().getRow());
    assertEquals(3, testDungeon.getEndPoint().getColumn());

    testDungeon = new DungeonImpl(true);
    assertEquals(3, testDungeon.getEndPoint().getRow());
    assertEquals(1, testDungeon.getEndPoint().getColumn());

  }

  @Test
  public void testGetMovesAtCaveIndex() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    ArrayList<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    ArrayList<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("South");
    // State should be deterministic at this point
    assertEquals(expectedOutput ,testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    expectedOutput = new ArrayList<>();
    expectedOutput.add("North");
    expectedOutput.add("West");
    // State should be deterministic at this point
    assertEquals(expectedOutput ,testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    // THIS TIME WITH WRAPPING AS TRUE
    testDungeon = new DungeonImpl(true);
    // Create a player
    testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    expectedOutput = new ArrayList<>();
    expectedOutput.add("West");
    expectedOutput.add("South");
    // State should be deterministic at this point
    assertEquals(expectedOutput ,testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    expectedOutput = new ArrayList<>();
    expectedOutput.add("North");
    expectedOutput.add("East");
    // State should be deterministic at this point
    assertEquals(expectedOutput ,testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

  }

  @Test
  public void testExpungeCaveTreasure() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    ArrayList<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    // State should be deterministic at this point
    ArrayList<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");

    for (int i = 0; i < expectedOutput.size(); i++) {
      assertEquals(expectedOutput.get(i), caveTreasure.get(i).toString());
    }



  }

  @Test
  public void testPeekCaveTreasure() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    ArrayList<Treasure> caveTreasure = testDungeon.peekCaveTreasure(testPlayer.getPlayerLocation());
    // State should be deterministic at this point
    ArrayList<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");

    for (int i = 0; i < expectedOutput.size(); i++) {
      assertEquals(expectedOutput.get(i), caveTreasure.get(i).toString());
    }
  }

  @Test
  public void testGetCaveInDirection() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    ArrayList<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());

    // State should be deterministic at this point
    Point2D expectedOutput = testDungeon.getCaveInDirection(testPlayer.getPlayerLocation(), "S");
    assertEquals(1, expectedOutput.getRow());
    assertEquals(3, expectedOutput.getColumn());
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalGetCaveInDirection() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    ArrayList<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());

    // State should be deterministic at this point
    // Hence, an exception is expected!
    Point2D expectedOutput = testDungeon.getCaveInDirection(testPlayer.getPlayerLocation(), "N");
  }

  @Test
  public void testGameFinished() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // First test
    assertFalse(testDungeon.gameFinished(testPlayer.getPlayerLocation()));

    // Let's finish the game
    // <------> Move <------>
    ArrayList<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();


    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveWest();


    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();


    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

    // Game must be finished.
    assertTrue(testDungeon.gameFinished(testPlayer.getPlayerLocation()));




  }

}
