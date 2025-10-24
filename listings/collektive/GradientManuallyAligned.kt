fun <ID: Any> Aggregate<ID>.distanceTo(source: Boolean, metric: Field<ID, Double>) =
    alignedOn("Aggregate.distanceTo(Boolean)") { // We need to manually align to avoid clashing with other functions with a similar structure
        share(Double.POSITIVE_INFINITY) { distances ->
            alignedOn("share(Boolean)") { // We need to manually align again
                val actualMetrics = project(metric) // The field comes from another context, hence needs projection
                val throughNeighbor = distances.alignedMapValues(actualMetrics, Double::plus)
                when {
                    source -> alignedOn(true) { 0.0 }
                    else -> alignedOn(false) { throughNeighbor } // We cannot run the computation here or the source will never send data!
                }
            }
    }
}