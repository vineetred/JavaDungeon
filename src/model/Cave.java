package model;

import java.util.ArrayList;

class Cave {
  private final Point2D location;
  private int index;
  private int set;
  private ArrayList<Integer> neighborList;
  private ArrayList<Treasure> treasureList;
  // TODO: Add the array list that can store Monsters!
  private ArrayList<Monster> monsterList;
  private ArrayList<CrookedArrow> arrowList;


  protected Cave(int row, int column, ArrayList entrances, ArrayList neighborList,
                 ArrayList treasureList, int index, int set) {
    this.location = new Point2DImpl(row, column);
    this.index = index;
    this.set = set;
    this.neighborList = neighborList;
    this.treasureList = treasureList;
    this.monsterList = new ArrayList<>();
    this.arrowList = new ArrayList<>();

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

  // Takes a monster interface object and adds it to this cave!
  protected void addMonster(Monster monster) {
    this.monsterList.add(monster);
  }

  // Takes a monster interface object and adds it to this cave!
  protected void addCrookedArrow(CrookedArrow arrow) {
    this.arrowList.add(arrow);
  }

  // Returns the cave's monster list.
  // null if no monsters!
  protected ArrayList<Monster> getCaveMonsters() {
    return new ArrayList<>(this.monsterList);
  }


  // Check if arrows exists, if so, return the contents of the cave
  protected ArrayList<CrookedArrow> getCaveCrookedArrows() {
    if (this.arrowList.size() == 0) {
      return null;
    }
    else {
      return new ArrayList<>(this.arrowList);
    }
  }

  // Returns the cave's arrow list and set to empty!
  // null if no arrows!
  protected ArrayList<CrookedArrow> pickCaveCrookedArrows() {

    ArrayList<CrookedArrow> temporaryList = this.arrowList;
    this.arrowList = new ArrayList<>();
    return temporaryList;
  }
}
