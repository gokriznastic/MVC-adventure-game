package view;

import controller.GuiGameFeatures;

/**
 * This interface represents the view for the dungeon adventure game and lists the functions
 * that a view can perform.
 */
public interface GameView {

  /**
   * Method to set the features as listed in the controller so that view
   * can send appropriate delete callbacks in case of certain listener events.
   *
   * @param f the controller
   */
  void setFeatures(GuiGameFeatures f);

  /**
   * Method to reset the focus on the appropriate part of the view.
   */
  void resetFocus();

  /**
   * Method to destroy a view by setting it invisible and disposing it off.
   */
  void delete();

  /**
   * Method to repaint the view and all the enclosed components.
   */
  void refresh();

  /**
   * Method to create a message pop up in the view for
   * displaying errors and other relevant messages.
   *
   * @param err the message to be printed in pop up
   * @param title the title of the pop up
   * @param messageType the type of pop-up depending on the context
   */
  void showMessage(String err, String title, int messageType);
}