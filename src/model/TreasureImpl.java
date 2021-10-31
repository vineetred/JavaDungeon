package model;

public class TreasureImpl {
  enum TreasureType {
    RUBY, DIAMOND, SAPPHIRE
  }

  static class Ruby implements Treasure {
    @Override
    public Treasure getTreasure() {
      return new Ruby();
    }

    @Override
    public String toString() { return "Ruby"; }
  }

  static class Diamond implements Treasure {
    @Override
    public Treasure getTreasure() {
      return new Diamond();
    }

    @Override
    public String toString() { return "Diamond"; }
  }

  static class Sapphire implements Treasure {
    @Override
    public Treasure getTreasure() {
      return new Sapphire();
    }

    @Override
    public String toString() { return "Sapphire"; }
  }

  static class TreasureFactory { // Simple Java Factory pattern class. This class will produce animals.

    public static Treasure getTreasureFromEnum(TreasureType treasureType) { // Java Factory pattern static method. This method receives an AnimalType of type Enum and according with the value will produce a different kind of Animal.

      Treasure treasure = null;

      if (TreasureType.RUBY.equals(treasureType)) { // a Cat Animal will be produced if the enum entry has the value CAT.
        treasure = new Ruby();
      } else if (TreasureType.DIAMOND.equals(treasureType)) { // a Dog Animal will be produced if the enum entry has the value DOG.
        treasure = new Diamond();
      } else if (TreasureType.SAPPHIRE.equals(treasureType)) {
        treasure = new Sapphire();
      }
      return treasure;
    }

  }

}
