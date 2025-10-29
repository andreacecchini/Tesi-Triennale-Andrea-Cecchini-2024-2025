/**
 * Connect [source] to [destination] using the given [metric] to measure distances
 * and [neighborDirectionVectors] to get the direction to each neighbor.
 */
fun Aggregate<Int>.connect(
    source: Boolean,
    destination: Boolean,
    metric: () -> Field<Int, Double>,
    neighborDirectionVectors: () -> Field<Int, Vector2D>,
): Vector2D {
    val toDestination = distanceTo(destination, metric())
    val isOnShortestPath = shortestPath(source, toDestination)
    return when {
        isOnShortestPath -> {
            val neighborDistances = neighboring(toDestination)
            val minNeighborhoodDistance = neighborDistances.all.valueOfMinBy { (_, dist) -> dist }
            neighborDirectionVectors()
                .alignedMapValues(neighborDistances) { dir, dist ->
                    if (dist == minNeighborhoodDistance) dir else vectorZero
                }
                .all
                .fold(vectorZero) { acc, (_, v) -> acc + v }
        }
        else -> vectorZero
    }
}