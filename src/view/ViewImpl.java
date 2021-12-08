package view;

import model.Dungeon;
import model.PlayerImpl;
import model.Point2D;
import model.Point2DImpl;

import java.util.List;
import java.util.Map;

public class ViewImpl implements ViewInterface {


  private GameHUD gameHUDBuff;
  private NewGamePrompt newGamePrompt;

  public ViewImpl() {

  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                          Map<Point2D, Boolean> visited, PlayerImpl inputPlayer) {
    this.gameHUDBuff = new GameHUD(inputDungeon, inputRows, inputCols, "",
        (Point2DImpl) inputDungeon.getStartPoint(), inputPlayer);
  }

  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                         Map<Point2D, Boolean> visited, PlayerImpl inputPlayer) {
//    this.gameHUDBuff.initialize(inputDungeon, inputRows, inputCols, "",
//        (Point2DImpl) inputPlayer.getPlayerLocation(), inputPlayer);
    this.gameHUDBuff.generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        visited, (Point2DImpl) inputPlayer.getPlayerLocation());
  }

  @Override
  public List<String> startNewGame() {
    newGamePrompt = new NewGamePrompt();
    return newGamePrompt.getUserParameters();
  }


  @Override
  public String getUserIntention() {
    return null;
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
  public void closeProgram() {
    System.exit(0);
  }

  @Override
  public void displayUserMessage(String inputString) {
    this.gameHUDBuff.displayUserMessage(inputString);
  }
}
