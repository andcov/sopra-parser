package parser

import farms.*
import map.*

class FarmParser(val config : String) {
    val farms : List<Farm>? = null

    fun createFarms(map : SimulationMap) : Result<List<Farm>> = TODO()

    fun validateFarms(map : SimulationMap) : Boolean {
        val farms = farms!!
        val idToTile = map.idToTile
        val tiles = map.idToTile.values

        val validMinimumFarms = farms.isNotEmpty()

        val farmIds = farms.map { it.id }
        val farmNames = farms.map { it.name }
        val validFarmIds = farmIds.all { it >= 0 }
        val noRepeatingFarmIds = farmIds.size == farmIds.toSet().size
        val noRepeatingFarmNames = farmNames.size == farmNames.toSet().size

        val validFarmsteadTiles = farms.flatMap { it.farmsteads }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.FARMSTEAD }
        val validFieldTiles = farms.flatMap { it.fields }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.FIELD }
        val validPlantationTiles = farms.flatMap { it.plantations }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.PLANTATION }

        val validFarmsteadFarmIds = tiles.filter { it.category == TileType.FARMSTEAD }.map { it.farm ?: error("farmstead must have farm id") }.all { it in farmIds }
        val validFieldFarmIds = tiles.filter { it.category == TileType.FIELD }.map { it.farm ?: error("field must have farm id") }.all { it in farmIds }
        val validPlantationFarmIds = tiles.filter { it.category == TileType.PLANTATION }.map { it.farm ?: error("field must have farm id") }.all { it in farmIds }

        val machines = farms.flatMap { it.machines }
        val machineIds = machines.map { it.id }
        val machineNames = machines.map { it.name }
        val validMachineIds = machineIds.all { it >= 0 }
        val noRepeatingMachineIds = machineIds.size == machineIds.toSet().size
        val noRepeatingMachineNames = machineNames.size == machineNames.toSet().size

        val validMachineDurations = machines.map { it.duration }.all { it in 1..14 }

        val validMachineLocation = machines.map { idToTile[it.location] ?: error("field must have location id") }.all { it.category == TileType.FARMSTEAD && it.shed }

        return true
    }
}
