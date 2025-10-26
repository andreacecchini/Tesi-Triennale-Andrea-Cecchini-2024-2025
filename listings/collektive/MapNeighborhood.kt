// phi(localId=0, localValue=1, neighbors={1=1, 2=1, 3=1})
val f: Field<Int, Int> = mapNeighborhood { 1 } 
// A collapsing view over the field entries
// that includes only neighbors.
val onlyNeighbors = f.neighbors
// A collapsing view over the field entries 
// that includes the local entry and all neighbors.
val all = f.all