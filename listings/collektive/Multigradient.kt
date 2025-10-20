/**
Computes the distance from the current node to all sources in the network using the [multiGradientCast] algorithm.
 */
fun Aggregate<Int>.multiGradient(
    distanceSensor: CollektiveDevice<*>,
    environment: EnvironmentVariables,
): Map<Int, Double> {
    val isSource = environment.get<Boolean>("source")
    val sources = share(emptySet()) { neighborSources ->
        neighborSources.all.fold(emptySet()) { accumulated, neighborSet ->
            accumulated union neighborSet.value
        }.let { collected ->
            if (isSource) collected + localId else collected
        }
    }

    return multiGradientCast(
        sources = sources,
        local = if (localId in sources) 0.0 else POSITIVE_INFINITY,
        metric = with(distanceSensor) { distances() },
        accumulateData = { fromSource, toNeighbor, _ -> fromSource + toNeighbor },
    )
}