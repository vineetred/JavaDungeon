package view;

import model.Dungeon;
import model.PlayerImpl;
import model.Point2D;
import model.Point2DImpl;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewImpl implements ViewInterface {


  private GameHUD gameHUDBuff;
  private NewGamePrompt newGamePrompt;

  public ViewImpl() {

  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                          Map<String, Boolean> visited, PlayerImpl inputPlayer) {
    this.gameHUDBuff = new GameHUD(inputDungeon, inputRows, inputCols, "",
        (Point2DImpl) inputDungeon.getStartPoint(), inputPlayer, visited);
  }

  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                         Map<String, Boolean> visited, PlayerImpl inputPlayer) {
//    this.gameHUDBuff.initialize(inputDungeon, inputRows, inputCols, "",
//        (Point2DImpl) inputPlayer.getPlayerLocation(), inputPlayer);
    this.gameHUDBuff.generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        visited, (Point2DImpl) inputPlayer.getPlayerLocation());

    this.gameHUDBuff.initializePlayerStats("", inputPlayer.getPlayerWeapons().size(),
        inputPlayer.getPlayerTreasure().size(), inputPlayer.isAlive(), inputPlayer);
  }

  @Override
  public List<String> startNewGame() {
    newGamePrompt = new NewGamePrompt();
    return newGamePrompt.getUserParameters();
  }


  @Override
  public String getUserIntention() {

    if (this.gameHUDBuff.getUserPickUp()) {
      return "P";
    }

    else if (this.gameHUDBuff.getUserMove()) {
      return "M";
    }

    else if (this.gameHUDBuff.getUserShoot()) {
      return "S";
    }

    else if (!Objects.equals(this.gameHUDBuff.getUserChangeGame(), "")) {
      return this.gameHUDBuff.getUserChangeGame();
    }

    return "";

  }

  @Override
  public void resetUserDirection() {
    this.gameHUDBuff.resetUserDirection();
  }

  @Override
  public String getUserDirection() {
    return new String(gameHUDBuff.getUserInputDirection());
  }

  @Override
  public List<String> getUserShootingParameters() {
    return this.gameHUDBuff.getUserShootingParameters();
  }

  @Override
  public void resetUserPickUp() {
    this.gameHUDBuff.resetUserPickUp();
  }

  @Override
  public void resetUserMove() {
    this.gameHUDBuff.resetUserMove();
  }

  @Override
  public void resetUserShoot() {
    this.gameHUDBuff.resetUserShootingParameters();
  }

  @Override
  public void closeProgram() {
    System.exit(0);
  }

  @Override
  public void displayUserMessage(String inputString) {
    this.gameHUDBuff.displayUserMessage(inputString);
  }

  @Override
  public void setListeners(KeyListener keys, MouseListener clicks) {
    this.gameHUDBuff.setListeners(keys, clicks);
  }

  @Override
  public String getUserChangeGame() {
    return this.gameHUDBuff.getUserChangeGame();
  }

  @Override
  public void resetUserChangeGame() {
    this.gameHUDBuff.resetUserChangeGame();
  }
}
