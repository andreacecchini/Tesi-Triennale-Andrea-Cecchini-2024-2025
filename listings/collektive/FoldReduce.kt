/**
 * Folds the collapsed field into a single value, 
 * starting from initial and combining elements 
 * with the provided accumulator.
 */
inline fun <Destination, T> Collapse<T>.fold(
    initial: Destination, 
    crossinline accumulator: Accumulator<Destination, T>
): Destination

/**
 * Reduces the elements in this collapse 
 * (which excludes the local element, i.e., only peers)
 * into a single value by repeatedly applying reducer.
 */
inline fun <T : Any> CollapseNeighbors<T>.reduce(
    crossinline reducer: Reducer<T>
): T?