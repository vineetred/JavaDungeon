package model;

import java.io.Serializable;

class Edge implements Serializable {
  private final Cave cave1;
  private final Cave cave2;


  Edge(Cave cave1, Cave cave2) {
    this.cave1 = cave1;
    this.cave2 = cave2;
  }

  boolean compareSets() {
    return this.cave1.getSet() == this.cave2.getSet();
  }

  int getLeftSet() {
    return this.cave1.getSet();
  }

  int getRightSet() {
    return this.cave2.getSet();
  }

  int getLeftIndex() {
    return this.cave1.getIndex();
  }

  int getRightIndex() {
    return this.cave2.getIndex();
  }

  void addNeighbors() {
    this.cave1.addNeighbor(this.getRightIndex());
    this.cave2.addNeighbor(this.getLeftIndex());
  }

  @Override
  public String toString() {
    return cave1.getIndex() + "<->" + cave2.getIndex();
  }
}
