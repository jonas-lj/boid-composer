package dk.jonaslindstrom.boids;

import java.util.List;
import java.util.function.IntPredicate;

public final class Boid {

  private final IntPredicate updateIndicator;
  private double x, v;
  private final List<Rule> rules;
  private final PostProcessing postProcessing;

  /**
   * Create a new Boid.
   *
   * @param x Initial position
   * @param v Initial velocity
   * @param rules Rules for this boid
   * @param postProcessing Post processing method for this boid
   * @param updateIndicator Predicate which given the iteration number returns whether this boid should move or not
   */
  public Boid(double x, double v, List<Rule> rules, PostProcessing postProcessing, IntPredicate updateIndicator) {
    this.x = x;
    this.v = v;
    this.rules = rules;
    this.postProcessing = postProcessing;
    this.updateIndicator = updateIndicator;
  }

/**
 * Create a new Boid.
 *
 * @param x Initial position
 * @param v Initial velocity
 * @param rules Rules for this boid
 * @param postProcessing Post processing method for this boid
  */
  public Boid(double x, double v, List<Rule> rules, PostProcessing postProcessing) {
    this(x, v, rules, postProcessing, c -> true);
  }

  /**
   * Create a new copy of a given boid with a new position and velocity
   *
   * @param x New position
   * @param v New velocity
   * @param base Boid to copy
   */
  Boid(double x, double v, Boid base) {
    this.x = x;
    this.v = v;
    this.rules = base.rules;
    this.postProcessing = base.postProcessing;
    this.updateIndicator = base.updateIndicator;
  }

  public double getX() {
    return x;
  }

  public double getV() {
    return v;
  }

  Boid update(List<Boid> flock) {
    double y = x + v;
    double w = v;
    for (Rule rule : rules) {
      w += rule.applyAsDouble(flock, this);
    }
    return postProcessing.apply(new Boid(y, w, rules, postProcessing, updateIndicator));
  }

  Boid update(List<Boid> flock, int clock) {
    if (updateIndicator.test(clock)) {
      return update(flock);
    }
    return this;
  }

}
