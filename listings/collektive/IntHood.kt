/**
 * Computes integral (weighted sum) of neighbor vectors in the field.
 * This function combines the directions from all neighbors by applying their respective
 * [weights] and summing the resulting vectors. .
 */
fun Field<Int, Vector2D>.sumWeightedNeighbors(weights: Field<Int, Double>): Vector2D =
    alignedMapValues(weights) { point, weight -> point * weight }.all
        .fold(Vector2D(0.0 to 0.0)) { acc, entry -> acc + entry.value }

/**
 * Computes the spatial weight of a device given a [radius].
 * The spatial weight is defined as the area of the circle with the given [radius]
 * divided by the number of devices in the neighborhood.
 * If there is only one device in the neighborhood, the spatial weight is equal to the area of the circle.
 */
fun Aggregate<Int>.spatialWeight(radius: Double): Double {
    val neighborsSize = neighborhood().all.size
    val totalArea = PI * radius * radius
    return totalArea / neighborsSize
}