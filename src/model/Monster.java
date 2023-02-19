package model;

/**
 * This interface represents a Monster.
 * A monster in the game has properties like no. of arrow hits taken, its dwelling loaction.
 * A monster can eat the player if the player enters its dwelling cave.
 */
public interface Monster {

  /**
   * Method to get the dwelling location of the monster.
   *
   * @return the dwelling location
   */
  Location getDwellingLocation();

  /**
   * Method to get the no. of arrow hits taken by the monster.
   *
   * @return  the no. of arrow hits taken already
   */
  int getHitsTaken();

  /**
   * Method to check if the monster is alive or dead.
   *
   * @return true is the monster is alive, else false
   */
  boolean isAlive();
}
