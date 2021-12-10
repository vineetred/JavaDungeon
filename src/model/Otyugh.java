package model;

import java.io.Serializable;

/** Represents the interface that all types of monsters will
 * always implement. This allows us to easily add monsters in the future without having to
 * refactor any of the controller or model code. This was, we always have room to add new
 * features down the line.
 */
class Otyugh implements Monster, Serializable {

  private int hits;


  public Otyugh() {
    this.hits = 0;
  }


  @Override
  public void takeHit() {
    if (this.hits < 2) {
      this.hits ++;
    }
  }

  @Override
  public int getHits() {
    int temp;
    temp = this.hits;
    return temp;
  }

  @Override
  public int monsterType() {
    return 0;
  }
}