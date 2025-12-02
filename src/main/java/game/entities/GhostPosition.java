package game.entities;

import game.GameplayPanel;

public enum GhostPosition {

    UP_LEFT(new Position(0, 0)),
    UP_RIGHT(new Position(GameplayPanel.width,0)),
    DOWN_LEFT(new Position(0, GameplayPanel.height)),
    DOWN_RIGHT(new Position(GameplayPanel.width, GameplayPanel.height)),
    ;

    private final Position position;

    GhostPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
