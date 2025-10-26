val f2 = mapNeighborhood { 2 }
val f3 = mapNeighborhood { 3 }
// phi(localId=0, localValue=5, neighbors={1=5, 2=5, 3=5})
f2 + f3
// phi(localId=0, localValue=-1, neighbors={1=-1, 2=-1, 3=-1})
f2 - f3
// phi(localId=0, localValue=6, neighbors={1=6, 2=6, 3=6})
f2 * f3