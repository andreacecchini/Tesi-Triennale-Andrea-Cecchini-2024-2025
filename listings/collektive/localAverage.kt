/**
 * Computes the local average of vectors in the neighborhood of the current device.
 * Each device holds a point [v] in R^3 representing its current state.
 * The function returns the component-wise average of the vectors from the device and its neighbors.
 */
fun Aggregate<Int>.localAverage(v: Point3D): Point3D = with(neighboring(v).all) {
    fold(vectorZero) { acc, nbr -> acc + nbr.value } / size.toDouble()
}