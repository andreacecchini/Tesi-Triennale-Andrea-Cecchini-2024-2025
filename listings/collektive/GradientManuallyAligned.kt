fun <ID: Any> Aggregate<ID>.distanceTo(source: Boolean, metric: Field<ID, Double>) 
    = alignedOn("Aggregate.distanceTo(Boolean)") { // Avoid clashing with other functions with a similar structure
        share(Double.POSITIVE_INFINITY) { distances ->
            alignedOn("share(Boolean)") { // We need to manually align again on share operator.
                val actualMetrics = project(metric) // Fields need projection  
                val throughNeighbor = distances
                    .alignedMapValues(actualMetrics, Double::plus)
                when {
                    source -> alignedOn(true) { 0.0 } // Align on true
                    else -> alignedOn(false) { throughNeighbor } // Align on false
                }
            }
        }
    }