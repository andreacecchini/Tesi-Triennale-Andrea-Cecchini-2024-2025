val f = mapNeighborhood { 1 }
// Counting the number of devices in the neighboorhood (including self)
val count = f
  .all
  .fold(0) { acc, _ -> acc + 1 }