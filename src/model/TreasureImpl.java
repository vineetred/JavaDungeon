package model;

import java.io.Serializable;

/** An implementation of the treasure class
 * along with an enum class that is used throughout
 * the model and the driver.
 */
public class TreasureImpl implements Serializable {
  enum TreasureType {
    RUBY, DIAMOND, SAPPHIRE
  }

  static class Sapphire implements Treasure, Serializable {

    @Override
    public String toString() {
      return "Sapphire";
    }
  }

  static class Diamond implements Treasure, Serializable {

    @Override
    public String toString() {
      return "Diamond";
    }
  }

  static class Ruby implements Treasure, Serializable {

    @Override
    public String toString() {
      return "Ruby";
    }
  }



  static class TreasureGeneratorHelperClass {

    public static Treasure getTreasureFromEnum(TreasureType treasureType) {

      Treasure treasure = null;

      if (TreasureType.RUBY.equals(treasureType)) {
        treasure = new Ruby();
      }
      else if (TreasureType.DIAMOND.equals(treasureType)) {
        treasure = new Diamond();
      }
      else if (TreasureType.SAPPHIRE.equals(treasureType)) {
        treasure = new Sapphire();
      }
      return treasure;
    }

  }

}
