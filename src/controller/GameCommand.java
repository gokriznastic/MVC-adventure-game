package controller;

import model.GameModel;

/**
 * This interface represents a command in the dungeon adventure game controller,
 * using the command design pattern.
 */
public interface GameCommand {

  /**
   * Method that executes the game command.
   *
   * @param model a non-null dungeon adventure game model
   */
  void execute(GameModel model);
}
