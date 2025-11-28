package game.entities.direction;

public class Speeds {

    private int xSpeed;
    private int ySpeed;

    public Speeds(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public boolean isStopped() {
        return xSpeed == 0 && ySpeed == 0;
    }

    public boolean isDiagonal() {
        return Math.abs(xSpeed) == Math.abs(ySpeed);
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }
}
