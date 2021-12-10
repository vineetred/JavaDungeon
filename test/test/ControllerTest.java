package test;

import controller.Controller;
import controller.ControllerCLI;
import controller.ControllerGUI;
import model.Dungeon;
import model.DungeonImpl;

import model.Player;
import model.PlayerImpl;
import model.Point2DImpl;
import org.junit.Test;
import view.MockView;
import view.ViewInterface;

import java.io.StringReader;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** The helper controller test class that lets us test the various functionality of the controller.
 * Keep in mind that this mostly tests what a user might do; and not the functionality of the
 * underlying model itself. This is taken care in the other tests class. We go through invariant
 * testing and robust error recovery checks to see if we recover as expected.
 */
public class ControllerTest {

  @Test
  public void testGameExit() {

    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    // Will only be invoked if game exits!
    assertEquals("[26<->27, 15<->21, 14<->15, 20<->26, 28<->29, 13<->19, 4<->10, " +
            "12<->13, " +
            "25<->26, 18<->24, 2<->8, 19<->20, 1<->2, 1<->7, 16<->22, 8<->14, 23<->29, 0<->6, " +
            "27<->28, " +
            "21<->22, 16<->17, 20<->21, 11<->17, 24<->25, 10<->16, 5<->11, 3<->9, 6<->12, 9<->10]",
        d.toString());
  }

  @Test
  public void testBuildDungeon() {

    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = ctrl.buildDungeon(false, 5, 7, 1, 30, 3);

    String gameState = d.toString();
    String[] splitGameState = gameState.split(",");

    // Check if all nodes are created properly, along with paths!
    assertEquals(35, splitGameState.length);

  }

  @Test
  public void testGettingEatenByMonster() {

    StringReader input = new StringReader("M S M E M S M S M W");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    assertFalse(player.isAlive());

  }


  @Test
  public void testKillingAMonster() {

    StringReader input = new StringReader("M S M E M S M S S W 1 S W 1 M W");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    assertTrue(player.isAlive());
    assertTrue(d.gameFinished(player.getPlayerLocation()));

  }

  @Test
  public void testInvalidCharsAsInput() {

    StringReader input = new StringReader("asdjhkasdjkashd Q");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    // Making sure the state remains same!
    assertFalse(d.gameFinished(player.getPlayerLocation()));
    assertTrue(player.isAlive());
    assertEquals(3, player.getPlayerWeapons().size());
    assertEquals(0, player.getPlayerTreasure().size());

  }

  @Test
  public void testNegativeShootDistance() {

    // Negative shoot distance!
    StringReader input = new StringReader("S N -2 Q");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerCLI(input, output, null);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    assertEquals(3, player.getPlayerWeapons().size());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testGUIControllerException() {


    ControllerGUI ctrl = new ControllerGUI(null, null, null, 7, 7, 1,
        2, 25, new HashMap<>(), true);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, null);

    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    assertEquals(3, player.getPlayerWeapons().size());

  }

  @Test
  public void testControllerGUIPlayGame() {

    StringReader input = new StringReader("");
    StringBuilder output = new StringBuilder();

    ViewInterface view = new MockView(output);
    ControllerGUI ctrl = new ControllerGUI(input, output, view, 7, 7, 1,
        2, 25, new HashMap<>(), true);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getEndPoint(), d);
    ctrl.playGame(d, player, view);

    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    // Making sure the state remains same!
    assertTrue(d.gameFinished(player.getPlayerLocation()));
    assertFalse(player.isAlive());
    assertEquals(3, player.getPlayerWeapons().size());
    assertEquals(0, player.getPlayerTreasure().size());

  }

  @Test
  public void testControllerGUIExit() {

    StringReader input = new StringReader("");
    StringBuilder output = new StringBuilder();

    ViewInterface view = new MockView(output);
    ControllerGUI ctrl = new ControllerGUI(input, output, view, 7, 7, 1,
        2, 25, new HashMap<>(), true);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getEndPoint(), d);
    ctrl.playGame(d, player, view);

    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    // Making sure the state remains same!
    // Will only be invoked if game exits!
    assertEquals("[26<->27, 15<->21, 14<->15, 20<->26, 28<->29, 13<->19, 4<->10, " +
            "12<->13, " +
            "25<->26, 18<->24, 2<->8, 19<->20, 1<->2, 1<->7, 16<->22, 8<->14, 23<->29, 0<->6, " +
            "27<->28, " +
            "21<->22, 16<->17, 20<->21, 11<->17, 24<->25, 10<->16, 5<->11, 3<->9, 6<->12, 9<->10]",
        d.toString());
  }

  @Test
  public void testControllerShoot() {

    StringReader input = new StringReader("");
    StringBuilder output = new StringBuilder();

    // Mock view has built in shoot parameters which should reduce the player arrow count by 1.
    MockView view = new MockView(output);
    ControllerGUI ctrl = new ControllerGUI(input, output, view, 7, 7, 1,
        2, 25, new HashMap<>(), true);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getEndPoint(), d);
    ctrl.playGame(d, player, view);
    view.simulatePlayerShoot();


    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    // Making sure the state remains same!
    assertTrue(d.gameFinished(player.getPlayerLocation()));
    assertFalse(player.isAlive());
    assertEquals(2, player.getPlayerWeapons().size());
    assertEquals(0, player.getPlayerTreasure().size());

  }

  @Test
  public void testControllerGUIPlayerMove() {

    StringReader input = new StringReader("");
    StringBuilder output = new StringBuilder();

    // Mock view has built in shoot parameters which should reduce the player arrow count by 1.
    MockView view = new MockView(output);
    ControllerGUI ctrl = new ControllerGUI(input, output, view, 7, 7, 1,
        2, 25, new HashMap<>(), true);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getEndPoint(), d);
    ctrl.playGame(d, player, view);
    view.simulateMovePlayer();

    // We need to get a count of the user's arrows to verify that state remains same!
    // as the controller handles this error correctly.
    // Making sure the state remains same!
    assertEquals(new Point2DImpl(2, 3).toString(), player.getPlayerLocation().toString());
    assertFalse(d.gameFinished(player.getPlayerLocation()));
    assertFalse(player.isAlive());
    assertEquals(3, player.getPlayerWeapons().size());
    assertEquals(0, player.getPlayerTreasure().size());

  }


}
