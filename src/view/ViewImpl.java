package view;

import model.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class ViewImpl implements ViewInterface {


  private GameHUD gameHUDBuff;
  private NewGamePrompt newGamePrompt;

  public ViewImpl() {

  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols, List<Boolean> visited) {
    this.gameHUDBuff = new GameHUD(inputDungeon, inputRows, inputCols);
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
