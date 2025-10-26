/**
 * Counts the number of devices in the network 
 * by aggregating `1` from each device towards the sink.
 */
inline fun <ID : Any> Aggregate<ID>.countDevices(sink: Boolean): Int