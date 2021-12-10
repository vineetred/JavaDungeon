package view;

import java.util.HashMap;
import java.util.Map;

/** Represents the constants that are used through the view component of our MVC program. THese
 * are anything from pixel offsets to filepath(s). We simply use the placeholder to update assets
 * without having to change the underlying code to reflect changes.
 */
public final class Constants {

  public static final int OFFSET = 100;
  public static final String ARROW_IMAGE_FILEPATH = "dungeon-images/arrow-black.png";
  public static final String MONSTER_IMAGE_FILEPATH = "dungeon-images/otyugh.png";
  public static final String DIAMOND_IMAGE_FILEPATH = "dungeon-images/diamond.png";
  public static final String EMERALD_IMAGE_FILEPATH = "dungeon-images/emerald.png";
  public static final String RUBY_IMAGE_FILEPATH = "dungeon-images/ruby.png";
  public static final String BAD_SMELL_FILEPATH = "dungeon-images/stench02.png";
  public static final String SUPER_BAD_SMELL_FILEPATH = "dungeon-images/stench01.png";
  public static final String FOG_TILE_FILEPATH = "dungeon-images/blank.png";
  public static final String THIEF_IMAGE_FILEPATH = "dungeon-images/thief.png";
  public static final String PLAYER_IMAGE_FILEPATH = "dungeon-images/player.png";


  public static final Map<String, String> DIRECTION_IMAGE_FILEPATH = new HashMap<>();

  static {
    DIRECTION_IMAGE_FILEPATH.put("NSEW", "dungeon-images/color-cells/NSEW.png");
    DIRECTION_IMAGE_FILEPATH.put("NSE", "dungeon-images/color-cells/NSE.png");
    DIRECTION_IMAGE_FILEPATH.put("NSW", "dungeon-images/color-cells/NSW.png");
    DIRECTION_IMAGE_FILEPATH.put("NS", "dungeon-images/color-cells/NS.png");
    DIRECTION_IMAGE_FILEPATH.put("NW", "dungeon-images/color-cells/NW.png");
    DIRECTION_IMAGE_FILEPATH.put("N", "dungeon-images/color-cells/N.png");
    DIRECTION_IMAGE_FILEPATH.put("S", "dungeon-images/color-cells/S.png");
    DIRECTION_IMAGE_FILEPATH.put("SE", "dungeon-images/color-cells/SE.png");
    DIRECTION_IMAGE_FILEPATH.put("SEW", "dungeon-images/color-cells/SEW.png");
    DIRECTION_IMAGE_FILEPATH.put("SW", "dungeon-images/color-cells/SW.png");
    DIRECTION_IMAGE_FILEPATH.put("W", "dungeon-images/color-cells/W.png");
    DIRECTION_IMAGE_FILEPATH.put("NEW", "dungeon-images/color-cells/NEW.png");
    DIRECTION_IMAGE_FILEPATH.put("NE", "dungeon-images/color-cells/NE.png");
    DIRECTION_IMAGE_FILEPATH.put("E", "dungeon-images/color-cells/E.png");
    DIRECTION_IMAGE_FILEPATH.put("EW", "dungeon-images/color-cells/EW.png");

  }
}