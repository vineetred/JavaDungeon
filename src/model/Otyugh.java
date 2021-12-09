package model;

import java.io.Serializable;

class Otyugh implements Monster, Serializable {

  // TODO: Make sure a cave can hold this object
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
}