package clouds

import map.MapController

class CloudController(val clouds : List<Cloud>, val cloudMap : Map<Int, Cloud>, val mapController: MapController) {
    fun updateOnTick() : Unit = TODO()
    fun createCloud(c : Cloud) : Unit = TODO()

    private fun dissipate(c : Cloud) : Unit = TODO()
    private fun move(c : Cloud) : Unit = TODO()
    private fun merge(c1 : Cloud, c2 : Cloud) : Unit = TODO()
    private fun rain(c : Cloud) : Unit = TODO()
}