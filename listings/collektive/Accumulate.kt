/**
 * Aggregates a field of local values along a spanning tree built by descending the provided potential field.
 * The parent of the current node is selected by picking the minimum as provided by the [selectParent] comparator,
 * which by default selects the parent with the lowest potential. Data is accumulated using the [accumulateData] function.
 */
inline fun <reified ID : Any, reified Data, reified Potential : Comparable<Potential>> Aggregate<ID>.convergeCast(
    local: Data,
    potential: Potential,
    selectParent: Comparator<FieldEntry<ID, Potential>> = defaultComparator(),
    crossinline accumulateData: (Data, Data) -> Data,
): Data 

/**
 * Aggregate a field of local into the closest sink
 * along a spanning tree built using hopDistanceTo. 
 * Data is accumulated using the accumulateData function. 
 */
inline fun <ID : Any, Data> Aggregate<ID>.convergeCast(
    local: Data, 
    sink: Boolean, 
    crossinline accumulateData: (Data, Data) -> Data
): Data

/**
 * Counts the number of devices in the network 
 * by aggregating `1` from each device towards the sink.
 */
inline fun <ID : Any> Aggregate<ID>.countDevices(sink: Boolean): Int