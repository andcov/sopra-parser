package incidents

import map.MapController

class Drought(id : Int, tick : Int, mapController : MapController, val location : Int, val radius : Int) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}