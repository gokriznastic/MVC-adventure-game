package view;

/**
 * This enum class represents the type of visual artifacts that can be used to
 * make the dungeon adventure game GUI.
 */
enum ImageCategory {
  PLAYER("/assets/player.png"),
  OTYUGH("/assets/otyugh.png"),
  ARROW("/assets/arrow-black.png"),
  DIAMOND("/assets/diamond.png"),
  RUBY("/assets/ruby.png"),
  SAPPHIRE("/assets/sapphire.png"),
  STENCH_LOW("/assets/stench01.png"),
  STENCH_HIGH("/assets/stench02.png"),
  TUNNEL_EW("/assets/EW.png"),
  TUNNEL_NS("/assets/NS.png"),
  TUNNEL_ES("/assets/ES.png"),
  TUNNEL_WN("/assets/NW.png"),
  TUNNEL_NE("/assets/EN.png"),
  TUNNEL_SW("/assets/SW.png"),
  CAVE_ONE_N("/assets/N.png"),
  CAVE_ONE_S("/assets/S.png"),
  CAVE_ONE_E("/assets/E.png"),
  CAVE_ONE_W("/assets/W.png"),
  CAVE_MULTI_ESW("/assets/ESW.png"),
  CAVE_MULTI_NES("/assets/ENS.png"),
  CAVE_MULTI_NEW("/assets/ENW.png"),
  CAVE_MULTI_SWN("/assets/NSW.png"),
  CAVE_MULTI_NESW("/assets/ENSW.png"),
  BLANK("/assets/blank.png");

  private final String filePath;

  ImageCategory(String fp) {
    this.filePath = fp;
  }

  String getFilePath() {
    return this.filePath;
  }
}
