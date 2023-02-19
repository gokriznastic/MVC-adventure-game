package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

import model.Location;
import model.ReadonlyGameModel;

/**
 * This class represents a custom JPanel containing one grid location of the dungeon.
 */
class GridPanel extends JPanel {
  private final ReadonlyGameModel model;
  private final int row;
  private final int col;

  /**
   * Constructor for initializing a grid panel.
   *
   * @param m the read-only model
   * @param r the row index for grid panel
   * @param c the column index for the grid panel
   */
  public GridPanel(ReadonlyGameModel m, int r, int c) {
    this.row = r;
    this.col = c;
    this.model = m;
  }

  int getRow() {
    return row;
  }

  int getCol() {
    return col;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Location loc = this.model.getDungeon().getLocation(this.row, this.col);
    boolean player = this.model.getPlayer().getCurrentLocation().equals(loc)
            && this.model.getPlayer().isAlive();

    Graphics2D g2d = (Graphics2D) g;
    setPreferredSize(new Dimension(100, 100));

    Image image = new PanelImage(this, loc, player).getImage();
    g2d.drawImage(image, 0, 0, null);
  }
}