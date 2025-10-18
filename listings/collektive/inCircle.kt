/**
 * Determines if the current device (located in [location])
 * is within a circle of a specified radius from a [center] point.
 */
fun Aggregate<Int>.inCircle(center: Boolean, location: Point2D, metric: () -> Field<Int, Double>): Boolean =
    with(location) {
        val centerPos = gradientCast(
            source = center,
            local = this,
            metric = metric(),
        )
        distanceToSquared(centerPos) <= RADIUS.pow(2)
    }