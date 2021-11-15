package model;

public class Otyugh implements Monster {

  // TODO: Make sure a cave can hold this object
  private int hits;


  public Otyugh() {
    this.hits = 0;
  }


  void takeHit() {
    if (this.hits < 2) {
      this.hits ++;
    }
  }

  @Override
  public int getHealth() {
    int temp;
    temp = this.hits;
    return temp;
  }
}