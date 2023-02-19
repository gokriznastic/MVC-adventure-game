package model;

/**
 * This interface represents the complete Dungeon Adventure Game model and its functionalities.
 * Using the functionality provided the game can be played interactively.
 */
public interface GameModel extends ReadonlyGameModel {
  /**
   * Method to move the player in a given direction.
   *
   * @param d the direction to move the player
   * @throws IllegalArgumentException if the player cannot move in the given direction
   */
  void movePlayer(Direction d);

  /**
   * Method to pick the given item from the current location of the player, if available.
   *
   * @param i the item to be picked from current location
   * @throws IllegalArgumentException if the item is not present at the current location
   */
  void pickItem(Item i);

  /**
   * Method to shoot arrow in a given direction and distance
   * from the current location of the player.
   *
   * @param d         the direction to shoot the arrow in
   * @param distance  the no. of caves the arrow should travel
   *
   * @return  true if the arrow hits a monster, else false
   *
   * @throws IllegalArgumentException if the direction or distance is not valid
   * @throws IllegalStateException    if the player has already exhausted all his arrows
   *
   */
  boolean shootArrow(Direction d, int distance);
}
