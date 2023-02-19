package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;

import controller.GuiGameFeatures;
import model.Location;
import model.ReadonlyGameModel;

/**
 * This class represents a custom JPanel containing the dungeon adventure game map.
 * It uses grid layout with each grid location having another custom JPanel
 * representing a dungeon location. This class also adds mouse listener to all the grid locations.
 */
class DungeonPanel extends JPanel {
  private final ReadonlyGameModel model;

  public DungeonPanel(ReadonlyGameModel m) {
    this.model = m;

    Location[][] dungeon = this.model.getDungeon().getDungeonGrid();

    this.setLayout(new GridLayout(dungeon.length, dungeon[0].length, 0, 0));

    for (int row = 0; row < dungeon.length; row++) {
      for (int col = 0; col < dungeon[0].length; col++) {
        JPanel panel = new GridPanel(this.model, row, col);
        this.add(panel);
      }
    }
  }

  void addMouseListener(GuiGameFeatures f) {
    // create mouse adapter
    MouseAdapter clickAdapter = new GameMouseAdapter(f, this.model);

    for (Component c : this.getComponents()) {
      if (c instanceof GridPanel) {
        GridPanel p = (GridPanel) c;
        p.addMouseListener(clickAdapter);
      }
    }
  }
}
