/**
 * Computes the navigation gradient vector for a node,
 * determining its direction of movement.
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
        else -> vectorZero
    }
}
