package model;

import java.util.ArrayList;

class Cave {
  private final Point2D location;
  private int index;
  private int set;
  private ArrayList<Integer> neighborList;
  private ArrayList<Treasure> treasureList;

  protected Cave(int row, int column, ArrayList entrances, ArrayList neighborList,
                 ArrayList treasureList, int index, int set) {
    this.location = new Point2DImpl(row, column);
    this.index = index;
    this.set = set;
    this.neighborList = neighborList;
    this.treasureList = treasureList;

    if (entrances.size() == 2 && !treasureList.isEmpty()) {
      throw new IllegalStateException("Tunnels can not have treasure");
    }
  }


  protected int getRow() {
    return location.getRow();
  }

  protected int getColumn() {
    return location.getColumn();
  }

  protected int getIndex() {
    return this.index;
  }

  protected int getSet() {
    return this.set;
  }

  protected void changeSet(int set) {
    this.set = set;
  }

  protected void addNeighbor(int index) {
    this.neighborList.add(index);
  }

  protected ArrayList<Integer> getNeighbors() {
    return this.neighborList;
  }

  protected Point2D getLocation() {
    return this.location;
  }

  protected void addTreasure(Treasure treasure) {
    this.treasureList.add(treasure);
  }

  // Check if treasure exits, if so return it and set to empty.
  protected ArrayList<Treasure> pickCaveTreasure() {
    if (this.treasureList.size() == 0) {
      return null;
    }
    else {
      ArrayList<Treasure> temporaryList = this.treasureList;
      this.treasureList = new ArrayList<>();
      return temporaryList;
    }
  }

  // Check if treasure exists, if so, return the contents of the cave
  protected ArrayList<Treasure> getCaveTreasure() {
    if (this.treasureList.size() == 0) {
      return null;
    }
    else {
      return new ArrayList<>(this.treasureList);
    }
  }
}
