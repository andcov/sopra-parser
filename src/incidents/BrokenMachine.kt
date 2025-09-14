package incidents

import map.MapController
import farms.Machine

class BrokenMachine(id : Int, tick : Int, mapController : MapController, val machineId : Int, val duration : Int, val machines : List<Machine>) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}
