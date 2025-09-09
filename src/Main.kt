import java.io.File
import java.io.PrintWriter
import java.nio.file.Path
import kotlin.contracts.Effect
import kotlin.io.path.Path

fun main(args : Array<String>) {
    if (args.size == 1 && args.first() == "--help") {
        println("""
            The simulator is started with these command line parameters:
                --map                 Path to map config file (required)
                --farms               Path to farms config file (required)
                --scenario            Path to scenario config file (required)
                --start_year_tick     The tick start within a year (default 1, between 1 and 24)
                --max_ticks           Maximum allowed number of ticks (required, max 1000)
                --log_level           DEBUG, INFO or IMPORTANT
                --out                 Path to output file (default stdout)
                --help                Print this message and exit
        """.trimIndent())
        return
    }
    if (args.size % 2 != 0) error("each flag has an input")
    var cliArgs = args.toList().chunked(2).associate { it[0] to it[1] }.toMutableMap()

    val correctFlagStart = cliArgs.all { it.key.startsWith("--")}
    if (!correctFlagStart) error("flag does not start with --")

    cliArgs = cliArgs.map { (k, v) -> Pair(k.drop(2), v) }.associate { it }.toMutableMap()

    val correctFlags = cliArgs.all { it.key in listOf("map", "farms", "scenario", "start_year_tick", "out", "log_level", "max_ticks") }
    if (!correctFlags) error("unknown flag")

    for (flag in listOf("map", "farms", "scenario", "max_ticks", "log_level"))
        if (flag !in cliArgs) error("expected $flag flag")

    for (flag in listOf("map", "farms", "scenario", "out")) {
        if (flag !in cliArgs) continue
        val path = cliArgs[flag]!!
        if (path.first() !in listOf('\'', '"')) continue

        if (path.first() != path.last()) error("unclosed parentheses")

        cliArgs[flag] = path.replace(Regex("""^['"]|['"]$"""), "")
    }

    val maxTicks = cliArgs["max_ticks"]?.toIntOrNull() ?: error("max_ticks must be an int")
    if (maxTicks > 1000) error("max ticks must not exceed 1000")

    val startTickString = cliArgs["start_year_tick"] ?: "1"
    val startTick = startTickString.toIntOrNull() ?: error("max_ticks must be an int")
    if (startTick !in 1..24) error("start year tick must be in [1, 24]")

    val out = if ("out" in cliArgs) PrintWriter(File(cliArgs["out"]!!)) else PrintWriter(System.out)

    val logLevel = LogLevel.entries.find { it.name == cliArgs["log_level"]!! } ?: error("log_level must be in ${LogLevel.entries}")

    val a = Arguments(startTick, maxTicks, Path(cliArgs["map"]!!), Path(cliArgs["farms"]!!), Path(cliArgs["scenario"]!!), out, logLevel)

    println(a)
}

data class Arguments(
    val startTick: Int = 1,
    val maxTicks: Int,
    val mapConfigPath: Path,
    val farmConfigPath: Path,
    val scenarioConfigPath: Path,
    val out: PrintWriter = PrintWriter(System.out),
    val logLevel: LogLevel
 )


class MapParser(val config : String) {
    val simulationMap: SimulationMap? = null

    fun createMap() : Result<SimulationMap> = TODO()

    private fun validateMap() : Boolean {
        val simulationMap = simulationMap!!
        val tiles = simulationMap.tiles
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

class FarmParser(val config : String) {
    val farms : List<Farm>? = null

    fun createFarms(map : SimulationMap) : Result<List<Farm>> = TODO()

    fun validateFarms(map : SimulationMap) : Boolean {
        val farms = farms!!
        val idToTile = map.idToTile

        val validMinimumFarms = farms.isNotEmpty()

        val farmIds = farms.map { it.id }
        val farmNames = farms.map { it.name }
        val validFarmIds = farmIds.all { it >= 0 }
        val noRepeatingFarmIds = farmIds.size == farmIds.toSet().size
        val noRepeatingFarmNames = farmNames.size == farmNames.toSet().size

        val validFarmsteadTiles = farms.flatMap { it.farmsteads }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.FARMSTEAD }
        val validFieldTiles = farms.flatMap { it.fields }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.FIELD }
        val validPlantationTiles = farms.flatMap { it.plantations }.map { idToTile[it]?.category ?: error("non-existent tileId") }.all { it == TileType.PLANTATION }

        val validFarmsteadFarmIds = map.tiles.filter { it.category == TileType.FARMSTEAD }.map { it.farm ?: error("farmstead must have farm id") }.all { it in farmIds }
        val validFieldFarmIds = map.tiles.filter { it.category == TileType.FIELD }.map { it.farm ?: error("field must have farm id") }.all { it in farmIds }
        val validPlantationFarmIds = map.tiles.filter { it.category == TileType.PLANTATION }.map { it.farm ?: error("field must have farm id") }.all { it in farmIds }

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

data class Coordinate(val x : Int, val y : Int)

class MapController(val simulationMap: SimulationMap)

data class SimulationMap(val tiles : List<Tile>, val idToTile : Map<Int, Tile>)

data class Tile(
    val id : Int,
    val coordinate: Coordinate,
    val category : TileType,
    val farm: Int?,
    val airflow: Boolean,
    val direction: Int?,
    val maxCapacity : Int?,
    val plant : Plant?,
    val shed: Boolean,
    val fallowPeriod : Int?,
    val soilMoisture : Int,
    val amountSunlight : Int,
    val neighbours : Map<Int, Int>
)

interface Plant {
    fun calculateHarvestChange()
}

enum class FieldPlant(val harvestEstimate : Int) : Plant {
    POTATO(1000000) {
        override fun calculateHarvestChange() {
            TODO("Not yet implemented")
        }
    }
}

enum class TileType {
    FARMSTEAD,
    FIELD,
    MEADOW,
    PLANTATION,
    ROAD,
    VILLAGE
}

data class Cloud(val id : Int, val duration : Int, val location : Int, val amount : Int)

data class Farm(
    val id : Int,
    val name : String,
    val farmsteads: List<Int>,
    val fields: List<Int>,
    val plantations: List<Int>,
    val machines : List<Machine>,
    val plants : List<Plant>
)

data class Machine(
    val id : Int,
    val name : String,
//    val actions : List<Action>
    val plant : List<Plant>,
    val duration: Int,
    val tileId : Int,
    val location : Int,
//    val currentAction : Action
    val farmId : Int,
    val isReady : Int,
)

sealed class Incident(val id : Int, val tick : Int) {
    abstract fun apply(mapC : MapController)
}

class CloudCreation(id: Int, tick: Int, val location : Int, val radius : Int, val duration: Int, val amount: Int) : Incident(id, tick) {
    override fun apply(mapC : MapController) {
        TODO("Not yet implemented")
    }
}

class CityExpand(id: Int, tick: Int, val location : Int) : Incident(id, tick) {
    override fun apply(mapC : MapController) {
        TODO("Not yet implemented")
    }
}

class Drought(id: Int, tick: Int, val location : Int, val radius: Int) : Incident(id, tick) {
    override fun apply(mapC : MapController) {
        TODO("Not yet implemented")
    }
}

class BeeHappy(id: Int, tick: Int, val location : Int, val radius: Int, val effect: Int, val isFirst : Boolean) : Incident(id, tick) {
    override fun apply(mapC : MapController) {
        TODO("Not yet implemented")
    }
}

class BrokenMachine(id: Int, tick: Int, val duration : Int, val machineId : Int) : Incident(id, tick) {
    override fun apply(mapC : MapController) {
        TODO("Not yet implemented")
    }
}


enum class LogLevel {
    DEBUG,
    INFO,
    IMPORTANT
}
