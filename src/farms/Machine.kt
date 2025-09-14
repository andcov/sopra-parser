package farms

import plants.Plant

data class Machine(
    val id : Int,
    val name : String,
    val actions : List<Action>,
    val plant : List<Plant>,
    val duration: Int,
    val tileId : Int,
    var location : Int,
    var currentAction : Action,
    val farmId : Int,
    val isReady : Int,
)

