package dk.jonaslindstrom.boids;

import java.util.List;

public class Alignment implements Rule {

  private final double weight;

  public Alignment(double weight) {
    this.weight = weight;
  }

  @Override
  public double applyAsDouble(List<Boid> boids, Boid boid) {
    double flockDirection = boids.stream().mapToDouble(Boid::getV).sum() / (boids.size() - 1);
    double direction = flockDirection - boid.getV();
    return direction * weight;
  }
}
