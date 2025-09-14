package farms

import plants.FieldPlant

data class SowingPlan (
    val id : Int,
    val startTick : Int,
    val plant : FieldPlant,
    val fieldIDs : List<Int>,
    val location : Int,
    val radius : Int,
)