package view;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

  public static final int OFFSET = 100;
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