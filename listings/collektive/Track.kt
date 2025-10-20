/**
 * Computes the direction to the [target], relative to [destination].
 * If the device is not in the [channel], it returns a zero vector.
 * If the device is in the [channel] but not [destination], it returns a zero vector.
 * If the device is [destination], it returns the vector pointing to the [target].
 */
fun Aggregate<Int>.track(
    target: Boolean,
    destination: Boolean,
    channel: Boolean,
    coordinates: Point2D,
    metric: () -> Field<Int, Double>,
): Vector2D = when {
    channel -> {
        // Broadcast the target's coordinates through the channel
        val targetCoordinates = gradientCast(
            source = target,
            local = coordinates,
            metric = metric(),
        )
        if (destination) targetCoordinates - coordinates else vectorZero
    }
    else -> vectorZero
}