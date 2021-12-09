package model;

import java.io.Serializable;

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