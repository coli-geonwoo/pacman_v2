package game.entities;

import game.GameplayPanel;

public class Position {

    private static final int DESTROY_X_VALUE = -32;
    private static final int DESTROY_Y_VALUE = -32;
    private static final int GRID_THRESHOLD = 8;

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isSame(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void moveX(int dx, int size, int speed) {
        this.x += dx;
        if (x > GameplayPanel.width) {
            x = 0 - size + speed;
        }

        if (x < 0 - size + speed) {
            x = GameplayPanel.width;
        }
    }

    public void moveY(int dy, int size, int speed) {
        this.y += dy;
        if (y > GameplayPanel.height) {
            y = 0 - size + speed;
        }

        if (y < 0 - size + speed) {
            y = GameplayPanel.height;
        }
    }

    public boolean onTheGrid() {
        return (x%GRID_THRESHOLD == 0 && y%GRID_THRESHOLD == 0);
    }

    public boolean onGameplayWindow() { return !(x<=0 || x>= GameplayPanel.width || y<=0 || y>= GameplayPanel.height); }


    public void destroy() {
        this.x = DESTROY_X_VALUE;
        this.y = DESTROY_Y_VALUE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
