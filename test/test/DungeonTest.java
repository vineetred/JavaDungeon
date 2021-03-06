package test;

import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Point2D;
import model.Point2DImpl;
import model.Treasure;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The helper dungeon test class that lets us test the various functionality of the model. It
 * only can test public methods as the test class is in its own package, separate from the model
 * and the driver.
 */
public class DungeonTest {

  // Helper methods
  private static void playerPickTreasureFromCave(Player testPlayer,
                                                 List<Treasure> caveTreasure) {

    if (caveTreasure != null) {
      testPlayer.pickUpTreasure(caveTreasure);
    }

  }

  // Dungeon exception tests
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRowsAndColumns() {
    Dungeon testDungeon = new DungeonImpl(false, -1, -1, 0, 20, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFewerThanSixNodes() {
    Dungeon testDungeon = new DungeonImpl(false, 1, 4, 0, 20, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFewerThanThreeColumns() {
    Dungeon testDungeon = new DungeonImpl(false, 2, 2, 0, 20, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanTwentyTreasure() {
    Dungeon testDungeon = new DungeonImpl(false, 6, 9, 0, 10, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInterconnectivity() {
    Dungeon testDungeon = new DungeonImpl(false, 6, 9, -1, 40, 2);
  }

  // Dungeon Public method tests
  @Test
  public void testBothDungeonCreation() {

    // Non-wrapping
    Dungeon testDungeon = new DungeonImpl(false);

    // Check if the paths are connecting every node
    assertEquals("[26<->27, 15<->21, 14<->15, 20<->26, 28<->29, 13<->19, 4<->10, " +
        "12<->13, 25<->26, 18<->24, 2<->8, 19<->20, 1<->2, 1<->7, 16<->22, 8<->14, 23<->29," +
        " 0<->6, 27<->28, 21<->22, 16<->17, 20<->21, 11<->17, 24<->25, 10<->16, 5<->11, 3<->9," +
        " 6<->12, 9<->10]", testDungeon.toString());

    // Wrapping
    testDungeon = new DungeonImpl(true);

    // Check if the paths are connecting every node
    // Paths such as 0<->6 would not be possible without wrapping
    // We know the paths as these are generated non-deterministic dungeons
    assertEquals("[22<->23, 27<->3, 3<->4, 11<->17, 3<->9, 13<->14, 18<->24, 1<->2, " +
        "13<->19, 8<->14, 12<->18, 12<->13, 8<->9, 16<->22, 15<->21, 24<->25, 0<->6, 14<->20, " +
        "0<->1, 15<->16, 14<->15, 10<->11, 11<->6, 25<->26, 16<->17, 7<->13, 5<->11, 27<->28, " +
        "28<->29]", testDungeon.toString());
  }

  @Test
  public void testCaveReachability() {

    Dungeon test = new DungeonImpl(false, 1, 4, 3);
    // Check if all nodes can be reached!
    assertEquals("[9<->10, 0<->1, 4<->5, 5<->8, 10<->11, 3<->6, 6<->9, 6<->7, 1<->4, 7<->8, 1<->2]",
        test.toString());

    // Create a player
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);

    Set<String> allNodes = new HashSet<>();

    testPlayer.moveWest();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveEast();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveEast();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveWest();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveSouth();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveEast();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveSouth();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveWest();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveWest();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveNorth();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveSouth();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveSouth();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveEast();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    testPlayer.moveEast();
    allNodes.add(testPlayer.getPlayerLocation().toString());
    assertEquals(3, testPlayer.getPlayerLocation().getRow());
    assertEquals(2, testPlayer.getPlayerLocation().getColumn());
    // Check if the nodes are 2 * 3 unique
    assertEquals(12, allNodes.size());

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
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    ArrayList<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("South");
    // State should be deterministic at this point
    assertEquals(expectedOutput, testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    expectedOutput = new ArrayList<>();
    expectedOutput.add("North");
    expectedOutput.add("East");
    // State should be deterministic at this point
    assertEquals(expectedOutput, testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    // THIS TIME WITH WRAPPING AS TRUE
    testDungeon = new DungeonImpl(true);
    // Create a player
    testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    expectedOutput = new ArrayList<>();
    expectedOutput.add("North");
    expectedOutput.add("East");
    expectedOutput.add("South");
    // State should be deterministic at this point
    assertEquals(expectedOutput, testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    // Going UP the wrapping dungeon from the top most row
    testPlayer.moveNorth();
    expectedOutput = new ArrayList<>();
    expectedOutput.add("South");
    expectedOutput.add("East");
    // State should be deterministic at this point
    assertEquals(expectedOutput, testDungeon.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

  }

  @Test
  public void testExpungeCaveTreasure() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
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
    List<Treasure> caveTreasure = testDungeon.peekCaveTreasure(testPlayer.getPlayerLocation());
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
  public void testMinimumTreasureThresholdInvariant() {

    // Check if the treasure threshold is being obeyed!
    Dungeon testDungeon = new DungeonImpl(false, 7, 7, 0, 100, 4);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    assertNotEquals(0, caveTreasure.size());


  }

  @Test
  public void testGetCaveInDirection() {
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());

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
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());

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
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

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
    testPlayer.moveWest();

    // Game must be finished.
    assertTrue(testDungeon.gameFinished(testPlayer.getPlayerLocation()));
  }

  @Test
  public void testInterconnectivityByCounting() {

    // Non-wrapping
    Dungeon testDungeon = new DungeonImpl(false);

    // Check if there is an edge for every node
    assertEquals("[26<->27, 15<->21, 14<->15, 20<->26, 28<->29, 13<->19, 4<->10, " +
        "12<->13, 25<->26, 18<->24, 2<->8, 19<->20, 1<->2, 1<->7, 16<->22, 8<->14, 23<->29," +
        " 0<->6, 27<->28, 21<->22, 16<->17, 20<->21, 11<->17, 24<->25, 10<->16, 5<->11, 3<->9," +
        " 6<->12, 9<->10]", testDungeon.toString());

    // Wrapping
    testDungeon = new DungeonImpl(true);

    // Check if there is an edge for every node
    assertEquals("[22<->23, 27<->3, 3<->4, 11<->17, 3<->9, 13<->14, 18<->24, 1<->2, " +
        "13<->19, 8<->14, 12<->18, 12<->13, 8<->9, 16<->22, 15<->21, 24<->25, 0<->6, 14<->20, " +
        "0<->1, 15<->16, 14<->15, 10<->11, 11<->6, 25<->26, 16<->17, 7<->13, 5<->11, 27<->28, " +
        "28<->29]", testDungeon.toString());


    // Wrapping but checking with truly random seed
    testDungeon = new DungeonImpl(true, 7, 7, 5, 20, 4);
    String dungeonString = testDungeon.toString();
    String arrow = "<->";

    // Counting the number of edges
    assertEquals(53, dungeonString.split(arrow, -1).length - 1);

    // Non - Wrapping but checking with truly random seed
    testDungeon = new DungeonImpl(false, 7, 7, 5, 20, 4);
    dungeonString = testDungeon.toString();
    arrow = "<->";

    // Counting the number of edges
    assertEquals(53, dungeonString.split(arrow, -1).length - 1);

    // Non - Wrapping but checking with truly random seed
    testDungeon = new DungeonImpl(false, 7, 7, 3, 20, 4);
    dungeonString = testDungeon.toString();
    arrow = "<->";

    // Counting the number of edges
    assertEquals(51, dungeonString.split(arrow, -1).length - 1);
  }

  @Test
  public void testStartPointEndPointMinimumDistanceInvariant() {
    // Check if the treasure threshold is being obeyed!
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    assertEquals("0,3", testDungeon.getStartPoint().toString());

    assertNotEquals("1,3", testDungeon.getEndPoint().toString());
    // The end point in the deterministic graph MUST BE >= 5
    assertEquals("3,3", testDungeon.getEndPoint().toString());
  }

  // Point2D class testing
  @Test
  public void testPoint2DCreation() {
    Point2D testPoint = new Point2DImpl(1, 1) {
    };
    assertNotNull(testPoint);
  }

  @Test
  public void testPointGetRowAndGetColumn() {
    Point2D testPoint = new Point2DImpl(1, 9);
    assertEquals(1, testPoint.getRow());
    assertEquals(9, testPoint.getColumn());
  }

  // Player interface exception testing
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalPlayerCreation() {
    Player testPlayer = new PlayerImpl(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalTreasurePickPlayer() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    testPlayer.pickUpTreasure(null);
  }

  // Player movement exceptions
  @Test(expected = IllegalStateException.class)
  public void testIllegalMovement() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // All walls!
    testPlayer.moveNorth();
    testPlayer.moveEast();
    testPlayer.moveWest();

  }

  // Player interface testing
  @Test
  public void testPlayerCreation() {

    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    // Same start points
    assertEquals(0, testPlayer.getPlayerLocation().getRow());
    assertEquals(3, testPlayer.getPlayerLocation().getColumn());
  }

  @Test
  public void testGetPlayerLocation() {

    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    // Same start points
    assertEquals(0, testPlayer.getPlayerLocation().getRow());
    assertEquals(3, testPlayer.getPlayerLocation().getColumn());
  }

  @Test
  public void testPlayerPickUpTreasure() {
    // With a random non-wrapping dungeon!
    Dungeon testDungeon = new DungeonImpl(false, 1, 5, 6);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    List<Treasure> expectedOutput = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    testPlayer.pickUpTreasure(expectedOutput);
    // State should be deterministic at this point
    // Since the same object is assigned, this should always be true regardless
    // of the contents. Hence, we use a random treasure seed!
    assertEquals(expectedOutput, testPlayer.getPlayerTreasure());

    // With a random non-wrapping dungeon!
    testDungeon = new DungeonImpl(true, 1, 5, 6);
    testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    expectedOutput = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    testPlayer.pickUpTreasure(expectedOutput);
    // State should be deterministic at this point
    // Since the same object is assigned, this should always be true regardless
    // of the contents. Hence, we use a random treasure seed!
    assertEquals(expectedOutput, testPlayer.getPlayerTreasure());
  }

  @Test
  public void testGetPlayerTreasure() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    testPlayer.pickUpTreasure(testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation()));

    // State should be deterministic at this point
    // Note that we use the non-random treasure seed for this test
    List<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");
    expectedOutput.add("Diamond");

    for (int i = 0; i < expectedOutput.size(); i++) {
      // Assert every single object
      assertEquals(expectedOutput.get(i), testPlayer.getPlayerTreasure().get(i).toString());
    }
  }

  @Test
  public void testPlayerMovement() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // This sequence should not throw any exception!
    testPlayer.moveSouth();
    testPlayer.moveEast();
    // Game should still be running!
    assertFalse(testDungeon.gameFinished(testPlayer.getPlayerLocation()));
    testPlayer.moveSouth();
    testPlayer.moveSouth();
    testPlayer.moveWest();
    // We should have reached the end!
    assertTrue(testDungeon.gameFinished(testPlayer.getPlayerLocation()));

  }

  @Test
  public void testPlayerMoveNorth() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(true);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    testPlayer.moveNorth();
    assertEquals(4, testPlayer.getPlayerLocation().getRow());
    assertEquals(3, testPlayer.getPlayerLocation().getColumn());
  }

  @Test
  public void testPlayerMoveSouth() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(true);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    testPlayer.moveSouth();
    assertEquals(1, testPlayer.getPlayerLocation().getRow());
    assertEquals(3, testPlayer.getPlayerLocation().getColumn());
  }

  @Test
  public void testPlayerMoveEast() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(true);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    testPlayer.moveEast();
    assertEquals(0, testPlayer.getPlayerLocation().getRow());
    assertEquals(4, testPlayer.getPlayerLocation().getColumn());
  }

  @Test
  public void testPlayerMoveWest() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // This sequence should not throw any exception!
    testPlayer.moveSouth();
    testPlayer.moveEast();
    testPlayer.moveSouth();
    testPlayer.moveSouth();
    testPlayer.moveWest();

    assertEquals(3, testPlayer.getPlayerLocation().getRow());
    assertEquals(3, testPlayer.getPlayerLocation().getColumn());

  }

  // Treasure testing
  // There are no public methods in the Treasure interface.

  @Test
  public void testDungeonMonsterAddition() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(true);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    int numberOfMonsters = 0;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 6; col++) {
        if (testDungeon.peekCaveMonsters(new Point2DImpl(row, col)).size() > 0) {
          numberOfMonsters++;
        }
      }
    }

    assertEquals(1, numberOfMonsters);

  }

  @Test
  public void testCrookedArrowShoot() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // Let's finish the game
    // <------> Move <------>
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // Should be 0 as there must a wall in this direction
    // WE hit a wall
    assertEquals(0, testDungeon.shootWeapon(testPlayer.getPlayerLocation(),
        "W", 1));

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // Now we actually hit an Otyugh
    assertEquals(1, testDungeon.shootWeapon(testPlayer.getPlayerLocation(),
        "W", 1));

    // Twice
    assertEquals(2, testDungeon.shootWeapon(testPlayer.getPlayerLocation(),
        "W", 1));

  }

  @Test
  public void testPlayerIsAlive() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    assertTrue(testPlayer.isAlive());
  }

  @Test
  public void testPlayerDead() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // Let's finish the game
    // <------> Move <------>
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // Should be 0 as there must a wall in this direction
    assertEquals(0, testDungeon.shootWeapon(testPlayer.getPlayerLocation(),
        "W", 1));

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveWest();

    // Player should die as we fight a healthy monster head on
    testPlayer.fightMonster(testDungeon.peekCaveMonsters(testPlayer.getPlayerLocation()).get(0));

    assertFalse(testPlayer.isAlive());

  }

  @Test
  public void testArrowExpungeAndPlayerPickArrows() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);
    testPlayer.pickUpWeapons(testDungeon.expungeCaveWeapons(
        testPlayer.getPlayerLocation()));

    // State should be deterministic at this point
    // Note that we use the non-random treasure seed for this test
    // as this makes the number of arrows deterministic as well
    assertEquals(3, testPlayer.getPlayerWeapons().size());

  }

  @Test
  public void testSmellGenerationAndResetSmell() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    assertFalse(testDungeon.isMajorSmell(new Point2DImpl(3, 0)));
    assertFalse(testDungeon.isMinorSmell(new Point2DImpl(3, 0)));

    assertTrue(testDungeon.isMajorSmell(new Point2DImpl(3, 4)));
    assertTrue(testDungeon.isMinorSmell(new Point2DImpl(2, 4)));

    // Let's finish the game
    // <------> Move <------>
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // Killing the Otyugh at the end!
    testDungeon.shootWeapon(testPlayer.getPlayerLocation(), "W", 1);
    testDungeon.shootWeapon(testPlayer.getPlayerLocation(), "W", 1);

    testDungeon.resetSmell();

    // Dead otyugh means no smell!
    assertFalse(testDungeon.isMajorSmell(new Point2DImpl(3, 4)));
    assertFalse(testDungeon.isMinorSmell(new Point2DImpl(2, 4)));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeShootDistanceInDungeon() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    testDungeon.shootWeapon(testDungeon.getStartPoint(), "N", -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroAndNegativeMonstersException() {
    // With non-random dungeon
    // This should throw an exception!
    Dungeon testDungeon = new DungeonImpl(false, 6, 6, 2, 25, -2);
    // Create a player
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    testDungeon.shootWeapon(testDungeon.getStartPoint(), "N", 2);
  }

  @Test
  public void testPlayerGettingEatenByOtyugh() {
    // With non-random dungeon
    Dungeon testDungeon = new DungeonImpl(false);
    Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

    // Let's finish the game
    // <------> Move <------>
    List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveSouth();

    // <------> Move <------>
    caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);
    testPlayer.moveEast();

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
    testPlayer.moveWest();

    // They should get killed
    testPlayer.fightMonster(testDungeon.peekCaveMonsters(testPlayer.getPlayerLocation()).get(0));

    assertFalse(testPlayer.isAlive());
  }

  @Test
  public void testPlayerFightingInjuredOtyugh() {

    ArrayList<Boolean> outcomes = new ArrayList<>();

    // We perform this experiment 500 times
    for (int i = 0; i < 500; i++) {
      // With non-random dungeon
      Dungeon testDungeon = new DungeonImpl(false);
      Player testPlayer = new PlayerImpl(testDungeon.getStartPoint(), testDungeon);

      // Let's finish the game
      // <------> Move <------>
      List<Treasure> caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
      playerPickTreasureFromCave(testPlayer, caveTreasure);
      testPlayer.moveSouth();

      // <------> Move <------>
      caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
      playerPickTreasureFromCave(testPlayer, caveTreasure);
      testPlayer.moveEast();

      // <------> Move <------>
      caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
      playerPickTreasureFromCave(testPlayer, caveTreasure);
      testPlayer.moveSouth();

      // <------> Move <------>
      caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
      playerPickTreasureFromCave(testPlayer, caveTreasure);
      testPlayer.moveSouth();

      // Now we actually hit an Otyugh to injure it
      assertEquals(1, testDungeon.shootWeapon(testPlayer.getPlayerLocation(),
          "W", 1));

      // <------> Move <------>
      caveTreasure = testDungeon.expungeCaveTreasure(testPlayer.getPlayerLocation());
      playerPickTreasureFromCave(testPlayer, caveTreasure);
      testPlayer.moveWest();

      // They should now have a 50% chance of living
      testPlayer.fightMonster(testDungeon.peekCaveMonsters(testPlayer.getPlayerLocation()).get(0));

      outcomes.add(testPlayer.isAlive());
    }

    int trueCount = 0;

    for (Boolean outcome : outcomes) {
      if (outcome) {
        trueCount++;
      }
    }

    // We have a 40% minimum threshold to see if the fifty-fifty chance works.
    assertTrue(200 <= trueCount && trueCount <= 300);


  }
}
