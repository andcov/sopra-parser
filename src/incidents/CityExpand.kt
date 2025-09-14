package incidents

import clouds.CloudController
import map.MapController

class CityExpand(id : Int, tick : Int, mapController : MapController, val location : Int, val cloudController: CloudController) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}
