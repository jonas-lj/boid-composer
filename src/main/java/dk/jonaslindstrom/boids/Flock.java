package dk.jonaslindstrom.boids;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Flock {

  private final List<Boid> boids = new ArrayList<>();
  private int counter = 0;

  public void addBoid(Boid boid) {
    boids.add(boid);
  }

  public void update() {
    List<Boid> flock = new ArrayList<>(boids);
    boids.replaceAll(boid -> boid.update(flock, counter));
    counter++;
  }

  public List<Double> getPositions() {
    return boids.stream().map(Boid::getX).collect(Collectors.toList());
  }

}
