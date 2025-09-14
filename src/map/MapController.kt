package map

import clouds.CloudController

class MapController(val simulationMap: SimulationMap, val cloudController: CloudController) {
    fun tick() : Unit = TODO()

    private fun reduceMoisture() : Unit = TODO()
    private fun setSunLevel() : Unit = TODO()

    fun getTile(id : Int) : Tile = TODO()
    fun getNeighbourTile(id : Int) : Tile? = TODO()
    fun getNeighbourTiles(id : Int, radius : Int) : List<Tile> = TODO()
    fun findPath(t1 : Int, t2 : Int, isMachineFull : Boolean) : Boolean = TODO()
    fun getNextNearestTileOfType(id : Int) : List<Tile> = TODO()
}

data class SimulationMap(val idToTile : Map<Int, Tile>)