package test;

import controller.Controller;
import controller.ControllerImpl;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Point2D;
import model.Point2DImpl;
import model.Treasure;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ControllerTest {

  @Test
  public void testGameExit() {

    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerImpl(input, output);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player);

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

    Controller ctrl = new ControllerImpl(input, output);
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

    Controller ctrl = new ControllerImpl(input, output);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player);

    assertFalse(player.isAlive());

  }


  @Test
  public void testKillingAMonster() {

    StringReader input = new StringReader("M S M E M S M S S W 1 S W 1 M W");
    StringBuilder output = new StringBuilder();

    Controller ctrl = new ControllerImpl(input, output);
    Dungeon d = new DungeonImpl(false);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player);

    assertTrue(player.isAlive());
    assertTrue(d.gameFinished(player.getPlayerLocation()));

  }

}
