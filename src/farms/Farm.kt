package farms

import plants.Plant

data class Farm(
    val id : Int,
    val name : String,
    val farmsteads: List<Int>,
    val fields: List<Int>,
    val plantations: List<Int>,
    val machines : List<Machine>,
    val plants : List<Plant>,
    val sowingPlans : List<SowingPlan>
)
