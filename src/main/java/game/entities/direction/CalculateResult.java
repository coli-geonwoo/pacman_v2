package game.entities.direction;

public class CalculateResult {

    public static final CalculateResult DEFAULT_MAX_DISTANCE = new CalculateResult(Double.MAX_VALUE, null);

    private final double distance;
    private final Speeds speeds;

    public CalculateResult(double distance, Speeds speeds) {
        this.distance = distance;
        this.speeds = speeds;
    }

    public double getDistance() {
        return distance;
    }

    public Speeds getSpeeds() {
        return speeds;
    }
}
