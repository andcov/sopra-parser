package incidents

import map.MapController

class BeeHappy(id : Int, tick : Int, mapController : MapController, val location : Int, val radius : Int, val effect : Int, val isFirst : Boolean) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}
