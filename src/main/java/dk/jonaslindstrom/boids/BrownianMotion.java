package dk.jonaslindstrom.boids;

import java.util.List;
import java.util.Random;

public class BrownianMotion implements Rule {

  private final Random random;
  private final double weight;

  public BrownianMotion(double weight, Random random) {
    this.weight = weight;
    this.random = random;
  }

  @Override
  public double applyAsDouble(List<Boid> boids, Boid boid) {
    return weight * random.nextGaussian();
  }
}
