/**
 * Create a ring wave pattern originating from the [center] node.
 * The wave propagates outward from the center,
 * with a speed defined by [WAVE_SPEED],
 * a thickness defined by [WAVE_THICKNESS],
 * and a period defined by [WAVE_PERIOD].
 * It returns a boolean field indicating whether the ring
 * is active at each node.
 */
private fun Aggregate<Int>.ring(center: Boolean, currentTime: () -> Double, metric: () -> Field<Int, Double>): Boolean =
    run {
        val waveTime = broadcastTime(center, currentTime, metric)
        val distance = distanceTo(center, metric = metric())
        isRingActive(waveTime, distance)
    }

/**
 * Broadcast the current time from the [center] to all 
 * other nodes in the network,
 * based on the given [metric].
 */
private fun Aggregate<Int>.broadcastTime(
    center: Boolean,
    currentTime: () -> Double,
    metric: () -> Field<Int, Double>,
): Double = gradientCast(
    source = center,
    local = currentTime(),
    metric = metric(),
)

/**
 * Check if the ring is active at the given [waveTime] and [distance] 
 * from the center.
 * A ring is active if the distance from the center 
 * is within the wave thickness.
 */
private fun isRingActive(waveTime: Double, distance: Double): Boolean =
    abs(WAVE_SPEED * (waveTime % WAVE_PERIOD) - distance) < WAVE_THICKNESS