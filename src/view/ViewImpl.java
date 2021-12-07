package view;

import model.Dungeon;
import model.Point2DImpl;

import java.util.List;

public class ViewImpl implements ViewInterface {


  private GameHUD gameHUDBuff;
  private NewGamePrompt newGamePrompt;

  public ViewImpl() {

  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols, List<Boolean> visited) {
    this.gameHUDBuff = new GameHUD(inputDungeon, inputRows, inputCols, "asd",
        (Point2DImpl) inputDungeon.getStartPoint());
  }

  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, List<Boolean> visited) {
    this.gameHUDBuff.initialize(inputDungeon, inputRows, inputCols, "", (Point2DImpl) inputDungeon.getStartPoint());
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
  public String getUserDirection() {
    return null;
  }
}
