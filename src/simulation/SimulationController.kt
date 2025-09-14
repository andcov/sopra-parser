package simulation

import map.MapController
import incidents.IncidentController
import clouds.CloudController
import farms.FarmController
import utils.Stats

class SimulationController(val startTick : Int, val currentTick : Int, val maxTicks : Int, val mapController: MapController, val cloudController: CloudController, val farmController : List<FarmController>, val incidentController : IncidentController, val simulationStats : Stats) {
    fun runSimulation() : Unit = TODO()

    private fun tick() : Unit = TODO()
    private fun printFarmStats() : Unit = TODO()
    private fun printPlantStats() : Unit = TODO()
    private fun printRemainingHarvest() : Unit = TODO()
}