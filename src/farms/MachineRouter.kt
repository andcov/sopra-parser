package farms

import map.MapController

class MachineRouter(val farm : Farm, val mapController: MapController, val sowingPlans : Map<Int, List<Action>>, sowingActions: List<Action>) {
    fun executeOthers (schedule: Map<Int, List<Action>>) : Unit = TODO()
    fun executePlans(schedule: Map<Int, List<Action>>): Unit = TODO()

    private fun unpackPlansForCurrentTick(): Unit = TODO()
    private fun findNextActionOfType(act: Action): List<Action> = TODO()
    private fun canPerformAction(machine: Machine, a1: Action): Int? = TODO()
    private fun returnToShed(m: Machine) : Unit = TODO()
    private fun executeAction(m1: Machine, ac: Action) : Unit = TODO()
}