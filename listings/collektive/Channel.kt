/**
 * Compute the channel between the [source] and the [destination]
 * with a specific [channelWidth].
 */
fun Aggregate<Int>.channel(
    collektiveDevice: CollektiveDevice<*>,
    source: Boolean,
    destination: Boolean,
    channelWidth: Double,
): Boolean = with(collektiveDevice) {
    require(channelWidth.isFinite() && channelWidth > 0)
    val distances = distances()
    val toSource = distanceTo(source, metric = distances)
    val toDestination = distanceTo(destination, metric = distances)
    val sourceToDestination = broadcast(distances = distances, from = source, payload = toDestination)
    val channel = toSource + toDestination - sourceToDestination
    return if (channel.isFinite()) channel <= channelWidth else false
}
