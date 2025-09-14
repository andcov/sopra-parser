package incidents

import map.MapController

sealed class Incident(val id : Int, val tick : Int, val mapController : MapController) {
    abstract fun apply() : Unit
}