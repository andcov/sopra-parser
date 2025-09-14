package parser

import map.*

class MapParser(val config : String) {
    val simulationMap: SimulationMap? = null

    fun createMap() : Result<SimulationMap> = TODO()

    private fun validateMap() : Boolean {
        val simulationMap = simulationMap!!
        val tiles = simulationMap.idToTile.values
        val idToTile = simulationMap.idToTile

        val farmsteads = tiles.filter { it.category == TileType.FARMSTEAD }
        val meadows = tiles.filter { it.category == TileType.MEADOW }
        val fields = tiles.filter { it.category == TileType.FIELD }
        val plantations = tiles.filter { it.category == TileType.PLANTATION }
        val villages = tiles.filter { it.category == TileType.VILLAGE }

        val tileIds = tiles.map { it.id }
        val validTileIds = tileIds.all { it >= 0 }
        val noRepeatingTileIds = tileIds.size == tileIds.toSet().size

        val validCoordinatePairs = tiles.all { it.coordinate.x.mod(2) == it.coordinate.y.mod(2) }

        val validFarmTileShape = farmsteads.all { isSquareTile(it) }
        val validFarmTileNeighbours =
            farmsteads
                .flatMap { it.neighbours.values }
                .map { idToTile[it] ?: error("unexpected null neighbour") }
                .all { it.category !in listOf(TileType.FARMSTEAD, TileType.MEADOW) }
        val validShedIfNoFarmstead = tiles.filter { it.category != TileType.FARMSTEAD }.all { !it.shed }

        val validFieldTileShape = fields.all { isOctagonalTile(it) }

        val validMeadowTileShape = meadows.all { isSquareTile(it) }
        val validMeadowTileNeighbours =
            meadows
                .flatMap { it.neighbours.values }
                .map { idToTile[it] ?: error("unexpected null neighbour") }
                .all { it.category !in listOf(TileType.FARMSTEAD, TileType.MEADOW) }

        val validPlantationTileShape = plantations.all { isOctagonalTile(it) }

        val validDirectionIfAirflow = tiles.filter { it.airflow }.all { it.direction != null }
        val validDirectionIfNoAirflow = tiles.filter { !it.airflow }.all { it.direction == null }
        val validVillageAirflow = villages.all { !it.airflow }
        val validDirectionForSquare = tiles.filter { isSquareTile(it) }.mapNotNull { it.direction }.all { it in listOf(45, 135, 225, 315) }
        val validDirectionForOctagonal = tiles.filter { isOctagonalTile(it) }.mapNotNull { it.direction }.all { it in listOf(0, 45, 90, 135, 180, 225, 270, 315) }

        val validCapacity = tiles.mapNotNull { it.maxCapacity }.all { it >= 0 }

        return true
    }


    private fun isSquareTile(t: Tile) : Boolean = t.coordinate.x.mod(2) == 1 && t.coordinate.y.mod(2) == 1
    private fun isOctagonalTile(t: Tile) : Boolean = t.coordinate.x.mod(2) == 0 && t.coordinate.y.mod(2) == 0
}
