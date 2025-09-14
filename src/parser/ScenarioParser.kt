package parser

import clouds.Cloud
import incidents.*
import map.*
import farms.*

class ScenarioParser(val config : String) {
    val incidents : List<Incident>? = null
    val clouds : List<Cloud>? = null

    fun createClouds(map : SimulationMap) : Result<List<Cloud>> = TODO()
    fun createIncidents(map : SimulationMap) : Result<List<Incident>> = TODO()

    fun validateClouds(map : SimulationMap, farms : List<Farm>) : Boolean {
        val clouds = clouds!!
        val idToTile = map.idToTile

        val cloudIds = clouds.map { it.id }
        val validCloudIds = cloudIds.all { it >= 0 }
        val noRepeatingCloudIds = cloudIds.size == cloudIds.toSet().size

        val validLocations = clouds.all { it.location in idToTile }
        val nonVillageCloudLocations = clouds.map { idToTile[it.location]!! }.all { it.category != TileType.VILLAGE }
        val locations = clouds.map { it.location }
        val noRepeatingCloudLocations = locations.size == locations.toSet().size

        val validDurations = clouds.all { it.duration >= -1  }

        val validAmounts = clouds.all { it.duration > 0  }

        return true
    }

    fun validateIncidents(map : SimulationMap, farms : List<Farm>) : Boolean {
        val incidents = incidents!!
        val idToTile = map.idToTile

        val incidentIds = incidents.map { it.id }
        val validIncidentsIds = incidentIds.all { it >= 0 }
        val noRepeatingIncidentIds = incidentIds.size == incidentIds.toSet().size

        val validTick = incidents.all { it.tick >= 0 }

        val cloudCreation = incidents.mapNotNull { it as? CloudCreation }
        val brokenMachine = incidents.mapNotNull { it as? BrokenMachine }
        val beeHappy = incidents.mapNotNull { it as? BeeHappy }
        val drought = incidents.mapNotNull { it as? Drought }
        val cityExpand = incidents.mapNotNull { it as? CityExpand }

        val correctDurations = cloudCreation.all { it.duration >= -1 } && brokenMachine.all { it.duration >= -1 }

        val validLocationIds = cloudCreation.all { it.location in idToTile } && beeHappy.all { it.location in idToTile }
                && drought.all { it.location in idToTile } && cityExpand.all { it.location in idToTile }

        val validRadius = cloudCreation.all { it.radius >= 0 } && beeHappy.all { it.radius >= 0 } && drought.all { it.radius >= 0 }

        val validAmount = cloudCreation.all { it.amount > 0 }
        val validEffect = beeHappy.all { it.effect >= 0 }

        val machineIds = farms.flatMap { it.machines }.map { it.id }
        val validMachineIds = brokenMachine.all { it.machineId in machineIds }

        val idToTileType = idToTile.mapValues { it.value.category }.toMutableMap()
        val maxTick = incidents.maxOfOrNull { it.tick } ?: -1
        for (tick in 0..maxTick) {
            val currentIncidents = incidents.filter { it.tick == tick }
        }

        return true
    }
}

