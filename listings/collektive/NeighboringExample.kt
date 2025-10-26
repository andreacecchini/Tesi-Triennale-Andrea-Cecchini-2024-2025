val x = localComputation()
// phi(localId=0, localValue=x_0, neighbors={1=x_1, 2=x_2, 3=x_3})
val f = neighboring(x); 
// phi(localId=0, localValue=0, neighbors={1=0, 2=0, 3=0})
val f1 = neighborhood();
// phi(localId=0, localValue=x_0, neighbors={1=x_0, 2=x_0, 3=x_0})
val f2 = mapNeighborhood { x };