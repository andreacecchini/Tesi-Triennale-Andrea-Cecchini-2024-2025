/**
 * Wire the source and the destination with connections to the next hop on the shortest path, avoiding obstacles.
 */
fun Aggregate<Int>.wire(collektiveDevice: CollektiveDevice<*>, env: EnvironmentVariables): Unit =
    with(collektiveDevice) {
        val source: Boolean = env["src"]
        val destination: Boolean = env["dest"]
        val obstacle: Boolean = env["obstacle"]
        val hasObstacleInNeighborhood = neighboring(obstacle).all.any { it.value }
        val position = coordinates()
        val connectionDir = when {
            hasObstacleInNeighborhood && (!source && !destination) -> vectorZero
            else -> connect(
                source = source,
                destination = destination,
                metric = { distances() },
                neighborDirectionVectors = {
                    neighboring(position).alignedMapValues(mapNeighborhood { position }) { p, newO -> p - newO }
                },
            )
        }
        pointTo(connectionDir)
    }

