package game.entities.direction;

public class Speeds {

    private int xSpeed;
    private int ySpeed;

    public Speeds(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getDirection() {
        if (xSpeed > 0) {
            return 0;
        } else if (xSpeed < 0) {
            return 1;
        } else if (ySpeed < 0) {
            return 2;
        } else if (ySpeed > 0) {
            return 3;
        }
        throw new RuntimeException("Invalid direction");
    }

    public boolean isStopped() {
        return xSpeed == 0 && ySpeed == 0;
    }

    public boolean isDiagonal() {
        return Math.abs(xSpeed) == Math.abs(ySpeed);
    }

    public void updateSpeed(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }
}
