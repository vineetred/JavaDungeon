package view;

import model.Dungeon;
import model.PlayerImpl;

import java.util.List;
import java.util.Map;

/**
 * The View interface which is the high level component responsible for visualizing the dungeon
 * maze game. This interface has some private methods that help
 * it achieve the tasks outlined in the project description. This implementation also does not talk
 * to the player class objects at any point; this job is that of the controller class objects.
 * The views also have methods to allow easy refreshing. At no point does the view have any
 * ability to modify the state of the player or the dungeon.
 */
public interface ViewInterface {

  /** Build a dungeon HUD with the given parameters, dungeon and player.
   * This is what gets past on to the Controller in the driver code which is responsible to start
   * the game!
   * @param inputRows the number of rows
   * @param inputCols the number of columns
   * @param visited the hash set of all the nodes that we visited, so we only paint those
   * @param inputPlayer the player impl who is going to be playing in the dungeon
   * @param inputDungeon the dungeon that gets painted and played on.
   * @throws IllegalArgumentException if any of the arguments passed are invalid.
   */
  void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                   Map<String, Boolean> visited,
                   PlayerImpl inputPlayer);

  /** Build a dungeon HUD with the given parameters, dungeon and player.
   * This is what gets past on to the Controller in the driver code which is responsible to start
   * the game! The only difference between this and the previous method is that this does not
   * initialize any JPanels that were previously declared, making the refresh on the same JWindow.
   * @param inputRows the number of rows
   * @param inputCols the number of columns
   * @param visited the hash set of all the nodes that we visited, so we only paint those
   * @param inputPlayer the player impl who is going to be playing in the dungeon
   * @param inputDungeon the dungeon that gets painted and played on.
   * @throws IllegalArgumentException if any of the arguments passed are invalid.
   */
  void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited,
                  PlayerImpl inputPlayer);

  /** Get a new java swing window that takes parameters from the user. This is returned to the
   * form of a List that the calling program can manipulate.
   * @return List<String> of the game parameters.
   */
  List<String> startNewGame();

  /** Get the stringed form of the user intention that tells the controller what the user wants
   *  to based on the actions performed on the view. An empty string indicates a pending state.
   * @return List<String> of the game parameters.
   */
  String getUserIntention();

  /** Resets the user direction string to an empty string. Indicates that the user has either
   * made the choice of the direction they want to move in or empty when it is pending.
   */
  void resetUserDirection();

  /** Get the stringed form of the user direction that tells the controller what the user wants
   *  to based on the actions performed on the view. This can be easily reset using the methods
   *  described below. An empty string indicates a pending state.
   * @return String of the cardinal direction the player wants to move in.
   */
  String getUserDirection();

  /** Get a new java swing window that takes shooting parameters from the user. This is returned to
   *  the controller in form of a List that the calling program can manipulate. It contains the
   *  distance and the cardinal direction's symbol that is used to perform the shoot action on
   *  the player's weapon.
   * @return List<String> of the shooting parameters.
   */
  List<String> getUserShootingParameters();

  /** Resets the user intention to pick up the items present in the cave they currently find
   * themselves in. The state after the method returns is assumed by the controller to not want
   * to pick up anything in the cave that they are in.
   */
  void resetUserPickUp();

  /** Resets the user intention to move up from the cave they currently find
   * themselves in. The state after the method returns is assumed by the controller to not want
   * to move anywhere than the cave that they are in currently.
   */
  void resetUserMove();

  /** Resets the user intention to shoot from the cave they currently find
   * themselves in. The state after the method returns is assumed by the controller to not want
   * to shoot anywhere.
   */
  void resetUserShoot();

  /** A simple helper method used to indicate to the user some input message in the form of a
   * java swing dialog box.
   * @param inputString the string that the controller wants to display to the user.
   */
  void displayUserMessage(String inputString);

  /** Resets the user intention to restart the game, reuse the game or start a new game from
   * the one currently find themselves in. The state after this call resets the user intention to
   * the current game; making it the status quo.
   */
  void resetUserChangeGame();

}
