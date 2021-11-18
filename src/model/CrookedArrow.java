package model;

/** This represents a Weapon class which is used by the model and the controller.
 * It is a facade to hide the many other functionalities that may exist in the future.
 * This also allows for us to modify the underlying functionality and methods of the
 * implementation without breaking any other aspect of the code.
 */
public class CrookedArrow implements Weapon {

  public CrookedArrow() {

    // Empty constructor to account for future refactoring in case we need to add properties to the
    // crooked arrow class. This way, no other existing code needs to be refactored!
  }


}