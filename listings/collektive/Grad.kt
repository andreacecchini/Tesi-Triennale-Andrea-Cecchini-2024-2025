/**
 * Compute the gradient of a scalar field [v].
 */
fun Aggregate<Int>.grad(v: Double, neighborDistances: () -> Field<Int, Double>, neighborDirectionVectors: () -> Field<Int, Vector2D>): Vector2D {
    val differences = mapNeighborhood { v } - neighboring(v)
    val directions = neighborDirectionVectors()
    val distances = neighborDistances()
    return distances.alignedMapValues(
        differences, 
        directions, 
        { dist, diff, dir ->
            when {
                dist == 0.0 || !(abs(diff) < Double.POSITIVE_INFINITY) -> vectorZero
                else -> dir.normalize() * (diff / dist)
            }
        }).all.run {
            fold(vectorZero) { acc, (_, value) -> acc + value } / size.toDouble()
        }
}