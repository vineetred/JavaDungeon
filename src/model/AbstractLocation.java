package model;

import java.util.ArrayList;

public abstract class AbstractLocation implements Location {
  protected Point2D location;
  private ArrayList entrances;
  private ArrayList neighborList;
  private ArrayList treasureList;

  protected AbstractLocation(Point2D location, ArrayList entrances, ArrayList neighborList,
                             ArrayList treasureList) {
    this.location = location;
    //TODO - possibly remove 1
    this.entrances = entrances;
    this.neighborList = neighborList;
    this.treasureList = treasureList;
  }

  private void setLocation() {
  }
}
