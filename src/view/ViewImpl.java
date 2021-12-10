package view;

import model.Dungeon;
import model.PlayerImpl;
import model.Point2DImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The implementation of the View interface. This interface has some private methods that help
 * it achieve the tasks outlined in the View interface. This implementation also does not talk
 * to the player class objects at any point; this job is that of the controller class objects.
 * The views also have methods to allow easy refreshing. At no point does the view have any
 * ability to modify the state of the player or the dungeon.
 */
public class ViewImpl implements ViewInterface {


  private GameView gameViewBuff;
  private NewGamePrompt newGamePrompt;

  /**
   * The implementation of the View interface. This interface has some private methods that help
   * it achieve the tasks outlined in the View interface. This implementation also does not talk
   * to the player class objects at any point; this job is that of the controller class objects.
   * The views also have methods to allow easy refreshing. At no point does the view have any
   * ability to modify the state of the player or the dungeon.
   */
  public ViewImpl() {

  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                          Map<String, Boolean> visited, PlayerImpl inputPlayer) {
    this.gameViewBuff = new GameView(inputDungeon, inputRows, inputCols, "",
        (Point2DImpl) inputDungeon.getStartPoint(), inputPlayer, visited);
  }

  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                         Map<String, Boolean> visited, PlayerImpl inputPlayer) {

    this.gameViewBuff.generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        visited, (Point2DImpl) inputPlayer.getPlayerLocation());

    this.gameViewBuff.initializePlayerStats("", inputPlayer.getPlayerWeapons().size(),
        inputPlayer.getPlayerTreasure().size(), inputPlayer.isAlive(), inputPlayer);
  }

  @Override
  public List<String> startNewGame() {
    newGamePrompt = new NewGamePrompt();
    return newGamePrompt.getUserParameters();
  }


  @Override
  public String getUserIntention() {

    if (this.gameViewBuff.getUserPickUp()) {
      return "P";
    }

    else if (this.gameViewBuff.getUserMove()) {
      return "M";
    }

    else if (this.gameViewBuff.getUserShoot()) {
      return "S";
    }

    else if (!Objects.equals(this.gameViewBuff.getUserChangeGame(), "")) {
      return this.gameViewBuff.getUserChangeGame();
    }

    return "";

  }

  @Override
  public void resetUserDirection() {
    this.gameViewBuff.resetUserDirection();
  }

  @Override
  public String getUserDirection() {
    return new String(gameViewBuff.getUserInputDirection());
  }

  @Override
  public List<String> getUserShootingParameters() {
    return this.gameViewBuff.getUserShootingParameters();
  }

  @Override
  public void resetUserPickUp() {
    this.gameViewBuff.resetUserPickUp();
  }

  @Override
  public void resetUserMove() {
    this.gameViewBuff.resetUserMove();
  }

  @Override
  public void resetUserShoot() {
    this.gameViewBuff.resetUserShootingParameters();
  }

  @Override
  public void displayUserMessage(String inputString) {
    this.gameViewBuff.displayUserMessage(inputString);
  }

  @Override
  public void resetUserChangeGame() {
    this.gameViewBuff.resetUserChangeGame();
  }
}
