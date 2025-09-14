package incidents

import map.MapController
import clouds.CloudController

class CloudCreation(id : Int, tick : Int, mapController : MapController, val location : Int, val radius : Int, val duration : Int, val amount : Int, val cloudController: CloudController) : Incident(id, tick, mapController) {
    override fun apply() : Unit = TODO()
}
