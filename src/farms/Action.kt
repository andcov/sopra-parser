package farms

import map.Tile
import plants.Plant

sealed class Action(val plant : Plant, val startTick : Int, val endTick : Int, val tile : Tile) {
    abstract fun execute(schedule : Map<Int, List<Action>>)
}