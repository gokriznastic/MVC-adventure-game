package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import model.ReadonlyGameModel;

/**
 * This class represents a custom JPanel for displaying game information in
 * bottom panel of the game GUI.
 */
class InfoPanel extends JPanel {

  /**
   * Constructor for creating bottom panel having game information.
   *
   * @param rom the read-only model
   */
  public InfoPanel(ReadonlyGameModel rom) {
    this.setLayout(new BorderLayout());
    this.add(new PlayerInfoPanel(rom), BorderLayout.WEST);
    this.add(new LocationInfoPanel(rom), BorderLayout.EAST);
  }
}
