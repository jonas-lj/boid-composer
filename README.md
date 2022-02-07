## Algorithmic composition using the <i>Boids</i> algorithm

This software was used to create all voices for the album <i>Pieces of Infinity II</i> by Morten Bach and Jonas 
Lindstrøm.

The <a href="https://en.wikipedia.org/wiki/Boids">Boids</a> algorithm was invented by Craig Reynolds in 1986 to simulate the movement of flocks of 
birds. In this algorithm, each bird is controlled by few rules:

* Separation: Avoid crowding,
* Alignment: Move towards the average heading,
* Cohesion: Move towards the center of mass of the flock.

The algorithm is typically used in 2 or 3 dimensions (see eg. <a href="https://github.com/jonas-lj/Boids">this repository</a>
for an example), but in this case we work in 1 dimension. Each 
<i>Boid</i> is mapped to a musical note, generating a track with a voice per Boid in the flock. The 
output is stored as a MIDI-file.

 