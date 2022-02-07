package dk.jonaslindstrom.boids;

import java.util.List;

public class Separation implements Rule {

  private final double weight;
  private final double distance;

  public Separation(double distance, double weight) {
    this.distance = distance;
    this.weight = weight;
  }

  @Override
  public double applyAsDouble(List<Boid> flock, Boid boid) {
    double delta = 0.0;
    for (Boid other : flock) {

      if (other == boid) {
        continue;
      }

      double d = Math.abs(boid.getX() - other.getX());
      if (d < distance) {
        delta += 1.0 / d;
      }
    }

    return delta * weight;
  }

}
