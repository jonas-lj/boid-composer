package dk.jonaslindstrom.boids;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

public interface Rule extends ToDoubleBiFunction<List<Boid>, Boid> {

}
