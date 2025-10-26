val f = mapNeighborhood { 1 }
val f1 = mapNeighborhood { 2 }
val f2 = mapNeighborhood { 3 }

/* Manipulation */
// phi(localId=0, localValue=10, neighbors={1=2, 2=10, 3=2})
f.map { (id, value) -> if (id % 2 == 0) value * 10 else value + 1 }
// phi(localId=0, localValue=2, neighbors={1=2, 2=2, 3=2})
f.mapValues { value -> value + 1 }

/* Combination */
// phi(localId=0, localValue=5, neighbors={1=6, 2=5, 3=6})
f2.alignedMap(f3) { (id, v1), (_, v2) -> if (id % 2 == 0) v1 + v2 else v1 * v2 }
// phi(localId=0, localValue=5, neighbors={1=5, 2=5, 3=5})
f2.alignedMapValues(f3) { v1, v2 -> v1 + v2 }