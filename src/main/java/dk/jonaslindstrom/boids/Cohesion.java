package dk.jonaslindstrom.boids;

import java.util.List;

public class Cohesion implements Rule {

  private final double weight;

  public Cohesion(double weight) {
    this.weight = weight;
  }

  @Override
  public double applyAsDouble(List<Boid> boids, Boid boid) {
    double mean = boids.stream().mapToDouble(Boid::getX).sum() / (boids.size() - 1);
    double direction = mean - boid.getX();
    return direction * weight;
  }
}
