/**
 * Computes the Voronoi tessellation
 * based on a set of sources, producing a field of integers which identify
 * the region each device belongs to.
 * A device can take one of the following roles:
 * - **Vertex**: it is at the junction of three or more Voronoi cells.
 *   Its color will be [VERTEX_COLOR].
 * - **Border**: it is at the junction of two Voronoi cells.
 *   Its color will be [BORDER_COLOR].
 * - **Cell Member**: it is neither a vertex nor a border. Its color
 *   is calculated based on the ID of the closest source.
 * The sources are identified through environment variables.
 */
fun Aggregate<Int>.voronoi(collektiveDevice: CollektiveDevice<*>, env: EnvironmentVariables): Int {
    val closestSource = closestSource(collektiveDevice, env)
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
        else -> closestSource.toColor()
    }
}

/**
 * Find the closest source by computing a multi-gradient from all sources.
 * If there are no sources, return 0.
 */
private fun Aggregate<Int>.closestSource(collektiveDevice: CollektiveDevice<*>, env: EnvironmentVariables): Int =
    multiGradient(collektiveDevice, env).let { toSources ->
        toSources.minByOrNull { it.value }?.key ?: 0
    }