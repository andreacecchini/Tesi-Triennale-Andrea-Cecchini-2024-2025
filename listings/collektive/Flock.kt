/**
 * Implements flocking behavior.
 * The behavior is defined as follows:
 * - If a neighbor is closer than [CLOSE_NEIGHBOR_THRESHOLD] units, steer away from it.
 * - If a neighbor is farther than [FAR_NEIGHBOR_THRESHOLD] units, steer slightly towards it.
 * - Otherwise, align with the neighbor.
 * The resulting direction is normalized and combined with the current direction.
 */
fun Aggregate<Int>.flock(
    initialDirection: Vector2D,
    neighborDistances: () -> Field<Int, Double>,
    neighborDirectionVectors: () -> Field<Int, Vector2D>,
): Vector2D = share(vectorZero) { neighborVelocities ->
    val weights = neighboring(spatialWeight(CONNECTIVITY_RADIUS))
    val direction = neighborVelocities.alignedMapValues(
        neighborDistances(),
        neighborDirectionVectors(),
    ) { vel, dist, dir ->
        when {
            // steer away if too close
            dist > 0.0 && dist <= CLOSE_NEIGHBOR_THRESHOLD -> dir.normalize() * -1.0
            // steer slightly towards if too far
            dist > FAR_NEIGHBOR_THRESHOLD -> dir.normalize() * FAR_NEIGHBOR_ATTRACTION_WEIGHT
            // align if at a good distance
            else -> vel.normalize()
        }
    }.sumWeightedNeighbors(weights).normalize()
    (initialDirection + if (direction vdot direction > 0) direction else neighborVelocities.local.value).normalize()
}