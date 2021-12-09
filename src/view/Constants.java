package view;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

  public static final int OFFSET = 100;
  public static final String ARROW_IMAGE_FILEPATH = "src/dungeon-images/arrow-black.png";
  public static final String MONSTER_IMAGE_FILEPATH = "src/dungeon-images/otyugh.png";
  public static final String DIAMOND_IMAGE_FILEPATH = "src/dungeon-images/diamond.png";
  public static final String EMERALD_IMAGE_FILEPATH = "src/dungeon-images/emerald.png";
  public static final String RUBY_IMAGE_FILEPATH = "src/dungeon-images/ruby.png";
  public static final String BAD_SMELL_FILEPATH = "src/dungeon-images/stench02.png";
  public static final String SUPER_BAD_SMELL_FILEPATH = "src/dungeon-images/stench01.png";
  public static final String FOG_TILE_FILEPATH = "src/dungeon-images/blank.png";
  public static final String THIEF_IMAGE_FILEPATH = "src/dungeon-images/thief.png";


  public static final Map<String, String> DIRECTION_IMAGE_FILEPATH = new HashMap<>();

  static {
    DIRECTION_IMAGE_FILEPATH.put("NSEW", "src/dungeon-images/color-cells/NSEW.png");
    DIRECTION_IMAGE_FILEPATH.put("NSE", "src/dungeon-images/color-cells/NSE.png");
    DIRECTION_IMAGE_FILEPATH.put("NSW", "src/dungeon-images/color-cells/NSW.png");
    DIRECTION_IMAGE_FILEPATH.put("NS", "src/dungeon-images/color-cells/NS.png");
    DIRECTION_IMAGE_FILEPATH.put("NW", "src/dungeon-images/color-cells/NW.png");
    DIRECTION_IMAGE_FILEPATH.put("N", "src/dungeon-images/color-cells/N.png");
    DIRECTION_IMAGE_FILEPATH.put("S", "src/dungeon-images/color-cells/S.png");
    DIRECTION_IMAGE_FILEPATH.put("SE", "src/dungeon-images/color-cells/SE.png");
    DIRECTION_IMAGE_FILEPATH.put("SEW", "src/dungeon-images/color-cells/SEW.png");
    DIRECTION_IMAGE_FILEPATH.put("SW", "src/dungeon-images/color-cells/SW.png");
    DIRECTION_IMAGE_FILEPATH.put("W", "src/dungeon-images/color-cells/W.png");
    DIRECTION_IMAGE_FILEPATH.put("NEW", "src/dungeon-images/color-cells/NEW.png");
    DIRECTION_IMAGE_FILEPATH.put("NE", "src/dungeon-images/color-cells/NE.png");
    DIRECTION_IMAGE_FILEPATH.put("E", "src/dungeon-images/color-cells/E.png");
    DIRECTION_IMAGE_FILEPATH.put("EW", "src/dungeon-images/color-cells/EW.png");

  }
}