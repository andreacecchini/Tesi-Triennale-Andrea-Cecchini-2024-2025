/**
 * Represents an evolving pseudo-temperature field where 
 * each device updates its temperature over time.
 * Some devices act as fixed heat sources ([heatSource]) 
 * at [HEAT_SOURCE_TEMPERATURE], others as fixed cold sources
 * ([coldSource]) at [COLD_SOURCE_TEMPERATURE].
 * All other devices calculate their temperature dynamically as
 * the average temperature of their neighboring devices.
 */
fun Aggregate<Int>.temperature(heatSource: Boolean, coldSource: Boolean): Double =
    share(INITIAL_TEMPERATURE) { previousTemperatures ->
        val averageTemperature = previousTemperatures.all
            .values
            .sequence
            .average().takeIf { it.isFinite() } ?: INITIAL_TEMPERATURE
        when {
            heatSource -> HEAT_SOURCE_TEMPERATURE
            coldSource -> COLD_SOURCE_TEMPERATURE
            else -> averageTemperature
        }
    }
