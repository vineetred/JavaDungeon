package model;

/** Represents the interface that all types of monsters will
 * always implement. This allows us to easily add monsters in the future without having to
 * refactor any of the controller or model code. This was, we always have room to add new
 * features down the line.
 */
public interface Monster {

  /** Get the hits a given Monster has suffered in the form of an integer.
   * 2 means that the monster is dead; 1 means it is injured. 0 means it is healthy. RIP.
   * @return the number of hits the monster has suffered!
   */
  int getHits();

  /** Make the given monster object take a hit. That is, we increment the hit counter by exactly
   * one. If the monster is already dead, this does nothing. Think of what happens when you kill
   * a dead monster? Exactly.
   */
  void takeHit();

  /** Returns the integer value that represents the monster type.
   */
  int monsterType();
}