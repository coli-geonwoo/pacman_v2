package game.entities;

import java.awt.*;

//엔터티를 설명하는 추상 클래스
public abstract class Entity {
    protected int size;
    protected Position position;
    protected boolean destroyed = false;

    public Entity(int size, int xPos, int yPos) {
        this.size = size;
        this.position = new Position(xPos, yPos);
    }

    public void update() {}

    public void render(Graphics2D g) {}

    public void destroy() {
        this.position.destroy();
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getSize() {
        return size;
    }

    public int getxPos() {
        return position.getX();
    }

    public int getyPos() {
        return position.getY();
    }

    public abstract Rectangle getHitbox();
}
