package dk.jonaslindstrom.boids;

import java.util.List;

public class KeepInRange implements Rule {

  private final double D;
  private final double a, b;

  public KeepInRange(double a, double b, double D) {
    this.a = a;
    this.b = b;
    this.D = D;
  }

  @Override
  public double applyAsDouble(List<Boid> boids, Boid boid) {

    if (boid.getX() > b) {
      return -D * (boid.getX() - b);
    }

    if (boid.getX() < a) {
      return D * (a - boid.getX());
    }

    return 0;
  }
}
