package dk.jonaslindstrom.boids;

import dk.jonaslindstrom.mosef.midi.MIDIEncoder;
import dk.jonaslindstrom.mosef.modules.melody.Track;
import dk.jonaslindstrom.mosef.modules.scales.ScaleFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javax.sound.midi.InvalidMidiDataException;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Boids {

  public static void main(String[] arguments) throws InvalidMidiDataException, IOException {

    // Initialise PRNG
    Random random = new Random(1234);

    // Configuration
    int voices = 4;
    int duration = 128;
    double min = -0.5;
    double max = 0.5;

    Flock flock = new Flock();

    // Add brownian motion Boid to make movement more dynamic
    flock.addBoid(new Boid(random.nextDouble() * (max - min) + min, 0, List.of(
        new Cohesion(0.15),
        new BrownianMotion(0.01, random),
        new KeepInRange(min, max, 4.0)),
        new LimitVelocity(0.3)
    ));

    // Add other boids
    for (int i = 0; i < voices - 1; i++) {
      flock.addBoid(new Boid(random.nextDouble() * (max - min) + min, 0, List.of(
          new Alignment(0.05),
          new Cohesion(0.1),
          new Separation(0.2, 0.001),
          new KeepInRange(min, max, 2.0)),
          new LimitVelocity(0.3)
      ));
    }

    // Save positions in data matrix
    List<List<Double>> data = new ArrayList<>();
    for (int i = 0; i < duration; i++) {
      flock.update();
      data.add(flock.getPositions());
    }

    // Plot Boids
    XYChart chart = new XYChartBuilder().width(800).height(600).title(
        dk.jonaslindstrom.boids.Boids.class.getSimpleName()).xAxisTitle("i").yAxisTitle("x").build();
    for (int v = 0; v < voices; v++) {
      int finalV = v;
      chart.addSeries("Boid " + v, IntStream.range(0, duration).mapToDouble(i -> data.get(i).get(
          finalV)).toArray());
    }
    new SwingWrapper<>(chart).displayChart();

    // Create MIDI files
    double delta = .5; // Duration of each iteration
    for (int voice = 0; voice < voices; voice++) {
      Track track = new Track();
      int finalJ = voice;
      double[] values = IntStream.range(0, duration).mapToDouble(i -> data.get(i).get(finalJ)).toArray();

      int current = mapToNote(values[0]);
      int start = 0;

      for (int i = 1; i < values.length; i++) {

        // Only add new note if it changed from last iteration
        if (mapToNote(values[i]) != current) {
          track.addNote(current, start * delta, 0.5, (i - start) * delta);
          start = i;
          current = mapToNote(values[i]);
        }

      }
      track.addNote(current, start * delta, 0.5, (values.length - start) * delta);
      MIDIEncoder.encode(track, "boid" + voice + ".mid");
    }

  }

  public static int mapToNote(double value) {
    double scaled = (value + 1.4) * 5.0; // 28 notes -- two notes
    int out = ScaleFactory.wholeTone(28).noteAt((int) Math.floor(scaled));
    if (out > 127) {
      return 127;
    }
    return out;
  }
}
