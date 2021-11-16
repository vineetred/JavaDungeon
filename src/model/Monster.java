package model;

/** Represents the interface that all types of monsters will
 * always implement.
 *
 */
public interface Monster {

  // Get's the number of hits that an Otyugh has suffered.
  // Two means it ded.
  int getHits();

  void takeHit();
}