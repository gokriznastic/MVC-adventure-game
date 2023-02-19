package controller;

/**
 * This interface represents a set of features that the Dungeon Adventure Game offers.
 * Each feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed.
 *
 * <p>Each function is designed to take in the necessary data, if needed, to
 * complete that functionality.
 */

public interface GuiGameFeatures extends GameController {
  /**
   * Method to respond to events for moving the player and move the player in the model.
   *
   * @param direction the direction in which to move the player
   */
  void move(int direction);

  /**
   * Method to respond to events for picking up an item and picking it in the model.
   *
   * @param item  the item to picked by the player
   */
  void pick(int item);

  /**
   * Method to respond to events for shooting arrows and shooting it in a given direction
   * and distance in the model.
   *
   * @param direction the direction in which to shoot
   * @param distance  the distance over which to shoot
   */
  void shoot(int direction, String distance);

  /**
   * Method to restore the game to the state it started in.
   */
  void resetGame();

  /**
   * Method to start the game from scratch by making a new dungeon.
   */
  void restartGame();

  /**
   * Method to quit the game.
   */
  void quitGame();

  /**
   * Method to handle mouse clicks and move the player in the model.
   * @param xOne  the x-coordinates of the player location
   * @param yOne  the y-coordinates of the player location
   * @param xTwo  the x-coordinates of the location to be moved to
   * @param yTwo  the y-coordinates of the location to be moved to
   */
  void handleCellClick(int xOne, int yOne, int xTwo, int yTwo);
}
