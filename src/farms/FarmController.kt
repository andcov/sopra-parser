package farms

import map.MapController
import utils.Stats

class FarmController(val farm : Farm, val stats : Stats, val mapController: MapController, val schedule : Map<Int, List<Action>>, val router : MachineRouter) {
    fun updateOnTick() : Unit = TODO()
    fun updateHarvest(): Unit = TODO()
    private fun updateActions(): Unit = TODO()
    private fun getRemainingHarvestEstimate(): Int = TODO()
    private fun createIrrigating(): Unit = TODO()
}