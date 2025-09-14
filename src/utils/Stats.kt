package utils

import farms.Farm
import plants.Plant

class Stats(val statsPerFarm: Map<Farm, Int>, val statsPerPlant: Map<Plant, Int>) {
    fun recordStatsPerFarm(f: Farm, amt: Int): Unit = TODO()
    fun recordStatsPerPlant(plant: Plant, amt: Int): Unit = TODO()
    fun getPlantStats(plant: String): Int = TODO()
    fun getFarmStats(fId: Int): Int = TODO()
}