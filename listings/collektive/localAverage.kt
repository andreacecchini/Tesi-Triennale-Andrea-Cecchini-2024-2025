/**
 * Computes the local average of vectors in the neighborhood of the current device
 */
fun Aggregate<Int>.localAverage(v: Point3D): Point3D = with(neighboring(v).all) {
    fold(vectorZero) { acc, nbr -> acc + nbr.value } / size.toDouble()
}