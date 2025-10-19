/**
 * Compute the gradient of a scalar field [v].
 * The gradient is calculated using the differences in the values of [v]
 * between the node and its neighbors,
 * as well as the distances and directions to those neighbors.
 * The [neighborDistances] function provides the distances to neighboring nodes,
 * and the [neighborDirectionVectors] function provides the vectors pointing to neighboring nodes.
 * The function returns the gradient as a [Vector2D].
 */
fun Aggregate<Int>.grad(v: Double, neighborDistances: () -> Field<Int, Double>, neighborDirectionVectors: () -> Field<Int, Vector2D>): Vector2D {
    val differences = mapNeighborhood { v } - neighboring(v)
    val directions = neighborDirectionVectors()
    val distances = neighborDistances()
    return distances.alignedMapValues(differences, directions, { dist, diff, dir ->
        when {
            dist == 0.0 || !(abs(diff) < Double.POSITIVE_INFINITY) -> Vector2D(0.0 to 0.0)
            else -> dir.normalize() * (diff / dist)
        }
    }).all.run {
        fold(Vector2D(0.0 to 0.0)) { acc, (_, value) -> acc + value } / size.toDouble()
    }
}

/**
 * Share the distance from the [isCalculating] or [source] to all non [isCalculating] nodes.
 * The [neighborDistances] function provides the distances to neighboring nodes.
 * The function returns the computed distance as a Double.
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

/**
 * Computes the navigation gradient vector for a node,
 * determining its direction of movement.
 * The function's behavior depends on the [mover] and [source] boolean field:
 * - If a node is a [mover], the function computes a normalized vector
 *   pointing towards the [source] by following the gradient of a potential field.
 *   This vector represents the optimal direction of movement.
 * - If a node is not a [mover] or if the gradient is zero,
 *   the function returns a zero vector, indicating 
 *   that no movement should occur.
 * The calculation relies on the [neighborDistances] and [neighborDirectionVectors] functions to obtain the distances
 * and vectors pointing to neighboring nodes, respectively.
 */
fun Aggregate<Int>.navGrad(
    mover: Boolean,
    source: Boolean,
    neighborDistances: () -> Field<Int, Double>,
    neighborDirectionVectors: () -> Field<Int, Vector2D>,
): Vector2D = shareDistanceTo(!mover, source, neighborDistances).let { distance ->
    val g = grad(distance, neighborDistances, neighborDirectionVectors)
    when {
        mover && g.magnitude() > 0.0 -> g.normalize()
        else -> Vector2D(0.0 to 0.0)
    }
}
