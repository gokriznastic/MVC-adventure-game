package model;

import java.util.Map;

/**
 * This interface represents the Player.
 * The player in the game has properties like its current location and collected treasure so far.
 * The player can move in the dungeon and pick up treasure if available.
 */
public interface IPlayer {
  /**
   * Method to get the current location of the player on the game dungeon grid.
   *
   * @return the Location of the player
   */
  Location getCurrentLocation();

  /**
   * Method to get the treasure collected by the player and its quantity at any point in the game.
   *
   * @return a key value pair with the treasure as key and quantity as value
   */
  Map<Treasure, Integer> getTreasureCollected();

  /**
   * Method to get the no. of arrows left with the player.
   *
   * @return  the no. of arrows
   */
  int getArrowsLeft();

  /**
   * Method to check if the player is alive.
   *
   * @return true is player is alive, else false
   */
  boolean isAlive();
}
