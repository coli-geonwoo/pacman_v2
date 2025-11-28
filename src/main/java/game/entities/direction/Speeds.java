package game.entities.direction;

public class Speeds {

    private int xSpeed;
    private int ySpeed;

    public Speeds(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public boolean isSame(int xSpeed, int ySpeed) {
        return this.xSpeed == xSpeed && this.ySpeed == ySpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }
}
