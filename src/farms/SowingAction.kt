package farms

import map.Tile
import plants.Plant

class SowingAction(plant : Plant, startTick : Int, endTick : Int, tile : Tile, val sowingPlanId : Int) : Action(plant, startTick, endTick, tile){
    override fun execute(schedule : Map<Int, List<Action>>) : Unit = TODO()
}
