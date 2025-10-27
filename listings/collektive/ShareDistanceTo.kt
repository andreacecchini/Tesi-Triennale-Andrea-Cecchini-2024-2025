/**
 * Share the distance from the [isCalculating] or [source] to all non [isCalculating] nodes.
 */
fun Aggregate<Int>.shareDistanceTo(
    isCalculating: Boolean,
    source: Boolean,
    neighborDistances: () -> Field<Int, Double>,
): Double {
    val toSource = distanceTo(source, neighborDistances())
    val myDist = if (isCalculating) toSource else Double.POSITIVE_INFINITY
    val potentialDist = neighborDistances() + neighboring(myDist)
    val minDistance = potentialDist.all.valueOfMinBy { (_, value) -> value }
    return if (isCalculating) myDist else minDistance
}