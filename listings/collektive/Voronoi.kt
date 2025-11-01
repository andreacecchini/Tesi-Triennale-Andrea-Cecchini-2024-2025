/**
 * Computes the Voronoi tessellation (https://en.wikipedia.org/wiki/Voronoi_diagram)
 * based on a set of [source]s, producing a field of integers which identify
 * the region each device belongs to.
 * A device can take one of the following roles:
 * - **Vertex**: it is at the junction of three or more Voronoi cells.
 *   Its color will be [VERTEX_COLOR].
 * - **Border**: it is at the junction of two Voronoi cells.
 *   Its color will be [BORDER_COLOR].
 * - **Cell Member**: it is neither a vertex nor a border. Its color
 *   is calculated based on the ID of the closest source.
 */
fun Aggregate<Int>.voronoi(source: Boolean, metric: () -> Field<Int, Double>): Int {
    val closestSource = closestSource(source, metric)
    val neighborClosestSources = neighboring(closestSource)
    val distinctSources = neighborClosestSources.all
        .sequence
        .map { it.value }
        .toSet()
        .count()
    val isVertex = distinctSources >= 3
    val isBorder = distinctSources == 2
    return when {
        isVertex -> VERTEX_COLOR
        isBorder -> BORDER_COLOR
        else -> closestSource.toColor() // toColor() maps an ID to a color value
    }
}

/**
 * Find the closest source by computing a multi-gradient from all sources.
 * If there are no sources, return 0.
 */
private fun Aggregate<Int>.closestSource(source: Boolean, metric: () -> Field<Int, Double>): Int = gradientCast(
    source = source,
    local = localId,
    metric = metric(),
)
