package map

import plants.Plant
import plants.FieldPlant

data class Tile(
    val id : Int,
    val coordinate: Coordinate,
    val category : TileType,
    val farm: Int?,
    val airflow: Boolean,
    val direction: Int?,
    val maxCapacity : Int?,
    val plant : Plant?,
    val possiblePlants : List<FieldPlant>?,
    val harvestEstimate : Int,
    val startOfTickEstimate : Int,
    val shed: Boolean,
    val fallowPeriod : Int?,
    val soilMoisture : Int,
    val amountSunlight : Int,
    val neighbours : Map<Int, Int> // from degree to tile id
)

data class Coordinate(val x : Int, val y : Int)

enum class TileType {
    FARMSTEAD,
    FOREST,
    FIELD,
    MEADOW,
    PLANTATION,
    ROAD,
    VILLAGE
}