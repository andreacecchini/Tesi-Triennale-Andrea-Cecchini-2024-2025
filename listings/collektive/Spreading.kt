/**
 * Computes a fast, self-healing gradient broadcast of local values 
 * from all source nodes, always retaining the 
 * data from the nearest source.
 */
inline fun <reified ID : Any, reified Type> Aggregate<ID>.gradientCast(
    source: Boolean,
    local: Type,
    metric: Field<ID, Double>,
    maxDiameter: Int = Int.MAX_VALUE,
    noinline accumulateData: (fromSource: Double, toNeighbor: Double, data: Type) -> Type = { _, _, data -> data },
    crossinline accumulateDistance: Reducer<Double> = Double::plus,
): Type

/**
 * For each ID in sources, propagates data from that source using
 * a fast-repair integer gradient, and collects the results in a map
 * from source ID to propagated value.
 */
inline fun <reified ID : Any, reified Value> Aggregate<ID>.multiGradientCast(
    sources: Iterable<ID>,
    local: Value,
    metric: Field<ID, Double>,
    maxDiameter: Int = Int.MAX_VALUE,
    noinline accumulateData: (fromSource: Double, toNeighbor: Double, data: Value) -> Value = { _, _, data -> data },
): Map<ID, Value>

/**
 * Computes the distance from the nearest source node as a `Double`.
 * The distance between neighboring devices is computed 
 * using the `metric` function and defaults to the hop-count metric.  
 */
 inline fun <reified ID : Any> Aggregate<ID>.distanceTo(
    source: Boolean,
    metric: Field<ID, Double> = hops().toDouble(),
    maxDiameter: Int = Int.MAX_VALUE,
): Double

