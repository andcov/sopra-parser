package farms

import map.Tile
import plants.Plant

class MowingAction(plant : Plant, startTick : Int, endTick : Int, tile : Tile) : Action(plant, startTick, endTick, tile){
    override fun execute(schedule : Map<Int, List<Action>>) : Unit = TODO()
}
