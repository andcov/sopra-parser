package parser

import simulation.SimulationController
import clouds.Cloud
import farms.Farm
import incidents.Incident
import map.SimulationMap
import java.io.PrintWriter
import java.nio.file.Path
import utils.LogLevel
import java.io.File
import kotlin.collections.component1
import kotlin.io.path.Path

class Parser(val args : Array<String>, var mapConfig : String? = null, var farmConfig : String? = null, var scenarioConfig : String? = null) {
    var parameters : Parameters? = null

    var simulationMap : SimulationMap? = null
    var farms : List<Farm>? = null
    var clouds : List<Cloud>? = null
    var scheduledIncidents : List<Incident>? = null

    var mapP : MapParser? = null
    var farmP : FarmParser? = null
    var scenarioP : ScenarioParser? = null

    fun build() : Result<SimulationController> = TODO()

    private fun parseCLI() : Boolean {
        if (args.size == 1 && args.first() == "--help") {
            println(
                """
            The simulator is started with these command line parameters:
                --map                 Path to map config file (required)
                --farms               Path to farms config file (required)
                --scenario            Path to scenario config file (required)
                --start_year_tick     The tick start within a year (default 1, between 1 and 24)
                --max_ticks           Maximum allowed number of ticks (required, max 1000)
                --log_level           DEBUG, INFO or IMPORTANT
                --out                 Path to output file (default stdout)
                --help                Print this message and exit
        """.trimIndent()
            )
            return false // TODO: have to signal that it was ok, but help
        }
        if (args.size % 2 != 0) error("each flag has an input")
        var cliArgs = args.toList().chunked(2).associate { it[0] to it[1] }.toMutableMap()

        val correctFlagStart = cliArgs.all { it.key.startsWith("--") }
        if (!correctFlagStart) error("flag does not start with --")

        cliArgs = cliArgs.map { (k, v) -> Pair(k.drop(2), v) }.associate { it }.toMutableMap()

        val correctFlags = cliArgs.all {
            it.key in listOf(
                "map",
                "farms",
                "scenario",
                "start_year_tick",
                "out",
                "log_level",
                "max_ticks"
            )
        }
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

        val logLevel = LogLevel.entries.find { it.name == cliArgs["log_level"]!! }
            ?: error("log_level must be in ${LogLevel.entries}")

        val a = Parameters(
            startTick, maxTicks,
            Path(cliArgs["map"]!!), Path(cliArgs["farms"]!!), Path(cliArgs["scenario"]!!), out, logLevel
        )

        return true
    }

    private fun readFiles() : Boolean = TODO()
}


data class Parameters(
    val startTick: Int = 1,
    val maxTicks: Int,
    val mapConfigPath: Path,
    val farmConfigPath: Path,
    val scenarioConfigPath: Path,
    val out: PrintWriter = PrintWriter(System.out),
    val logLevel: LogLevel
)
