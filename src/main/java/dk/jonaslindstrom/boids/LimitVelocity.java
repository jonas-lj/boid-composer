package dk.jonaslindstrom.boids;

public class LimitVelocity implements PostProcessing {

  private final double m;

  public LimitVelocity(double m) {
    this.m = m;
  }

  @Override
  public Boid apply(Boid boid) {
    if (boid.getV() > m) {
      return new Boid(boid.getX(), m, boid);
    } else if (boid.getV() < -m) {
      return new Boid(boid.getX(), -m, boid);
    }
    return boid;
  }
}
