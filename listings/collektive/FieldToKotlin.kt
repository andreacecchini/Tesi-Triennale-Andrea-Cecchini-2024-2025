with(mapNeighborhood { 1 }.all) {
  val l = list // to a Kotlin's list of field entries (deviceId, value)
  val s = set // to a Kotlin' set of field entries (deviceId, value)
  val seq = sequence // to a Kotlin's sequence of field entries (deviceId, value)
  val map = toMap() // to a Kotlin's map, where each entry is (deviceId -> value)
}