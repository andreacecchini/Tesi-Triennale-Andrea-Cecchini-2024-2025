/**
 * Check whenever the current node is on the path from [source] to destination.
 * [toDestination] is the distance to the destination.
 */
fun Aggregate<Int>.shortestPath(source: Boolean, toDestination: Double): Boolean = share(false) { nbrIsPath ->
    val minId = neighboring(toDestination).all.minBy { (_, value) -> value }.id
    val isOnShortestPath = neighboring(minId)
        .mapValues { it == localId }.and(nbrIsPath)
        .all
        .any { (_, value) -> value }
    when {
        source -> true
        else -> isOnShortestPath
    }
}