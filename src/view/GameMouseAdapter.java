package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.GuiGameController;
import controller.GuiGameFeatures;
import model.ReadonlyGameModel;

/**
 * This class represents a mouse adapter that captures
 * a click on the dungeon game grid and passes it to the controller for move feature.
 */
class GameMouseAdapter extends MouseAdapter {
  private final ReadonlyGameModel model;
  private final GuiGameController con;

  /**
   * Constructor for mouse adapter.
   *
   * @param f  the controller
   * @param m  the model
   */
  public GameMouseAdapter(GuiGameFeatures f, ReadonlyGameModel m) {
    this.model = m;
    this.con = (GuiGameController) f;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    GridPanel gp = (GridPanel) e.getComponent();
    int clickedX = gp.getRow();
    int clickedY = gp.getCol();

    int[] coord = this.model.getPlayer().getCurrentLocation().getCoordinates();
    int playerX = coord[0];
    int playerY = coord[1];

    if (!this.model.isGameOver()) {
      this.con.handleCellClick(clickedX, clickedY, playerX, playerY);
    }
  }
}
