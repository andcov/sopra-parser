package incidents

import farms.FarmController
import map.MapController

class AnimalAttack(id : Int, tick : Int, mapController : MapController, val location : Int, val radius : Int, val farmController: List<FarmController>) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}
