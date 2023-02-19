package model;

/**
 * This interface represents a read-only model for the Dungeon Adventure Game.
 * Using this interface all information about the game and its current state can be accessed.
 */
public interface ReadonlyGameModel {
  /**
   * Method to get the adventure game dungeon.
   *
   * @return  the adventure game dungeon.
   */
  IPlayer getPlayer();

  /**
   * Method to get the player in the adventure game.
   *
   * @return  the player in the adventure game.
   */
  IDungeon getDungeon();

  /**
   * Method to get the Location of the game starting point.
   *
   * @return  the start location of the dungeon
   */
  Location getGameStart();

  /**
   * Method to get the co-ordinates of the game ending point.
   *
   * @return  the end location of the dungeon
   */
  Location getGameEnd();

  /**
   * Method to return whether the game is over. The game is over when either the player reaches the
   * end location alive or if a monster eats a player.
   *
   * @return  true if the game is over, else false
   */
  boolean isGameOver();

  /**
   * Method to get a string representation for a bird's eye view of the dungeon having
   * the player, caves, tunnels, start and the end.
   * This representation is helpful to navigate the player through the dungeon.
   *
   * @return  the current game state as a string
   */
  String getGameState();
}
