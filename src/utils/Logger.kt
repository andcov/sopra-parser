package utils

import java.io.PrintWriter

object Logger {
    var logLevel : LogLevel = LogLevel.DEBUG
    var writer : PrintWriter =  PrintWriter(System.out)

    fun fileValid(filename: String): Unit = TODO()
    fun fileInvalid(filename: String): Unit = TODO()
    fun simulationStart(tick: Int): Unit = TODO()
    fun simulationEnd(tick: Int): Unit = TODO()
    fun tickStart(tick: Int, yearTick: Int): Unit = TODO()

    fun soilMoisture(amountField: Int, amountPlantation: Int): Unit = TODO()

    fun cloudRain(cloudID: Int, tileID: Int, amount: Int): Unit = TODO()
    fun cloudMovement(cloudID: Int, amount: Int, startTileID: Int, endTileID: Int): Unit = TODO()
    fun cloudMovementSunlight(startTileID: Int, amountSunlight: Int): Unit = TODO()
    fun cloudUnion(cloudIDFrom: Int, cloudIDTo: Int, cloudIDNew: Int, amount: Int, duration: Int, tileID: Int): Unit = TODO()
    fun cloudStuck(cloudID: Int, tileID: Int): Unit = TODO()
    fun cloudDissipation(cloudID: Int, tileID: Int): Unit = TODO()
    fun cloudPosition(cloudID: Int, tileID: Int, amountSunlight: Int): Unit = TODO()

    fun farmStart(farmID: Int): Unit = TODO()
    fun farmActiveSowingPlan(farmID: Int, sowingPlanIDs: List<Int>): Unit = TODO()
    fun farmAction(machineID: Int, actionType: String, tileID: Int, duration: Int): Unit = TODO()
    fun farmSowing(machineID: Int, plant: String, sowingPlanID: Int): Unit = TODO()
    fun farmHarvest(machineID: Int, plant: String, amount: Int): Unit = TODO()
    fun farmMachineReturn(machineID: Int, tileID: Int): Unit = TODO()
    fun farmMachineReturnFailed(machineID: Int, tileID: Int): Unit = TODO()
    fun farmMachineUnload(machineID: Int, plant: String, amount: Int): Unit = TODO()
    fun farmFinished(farmID: Int): Unit = TODO()

    fun incident(incidentID: Int, incidentType: String, affectedTiles: List<Int>): Unit = TODO()

    fun harvestEstimateMissed(tileID: Int, actions: List<String>): Unit = TODO()
    fun harvestEstimateChanged(tileID: Int, plant: String, amount: Int): Unit = TODO()

    fun simulationStatisticsStart(): Unit = TODO()
    fun simulationStatisticsFarm(farmID: Int, amount: Int): Unit = TODO()
    fun simulationStatisticsPlant(plant: String, amount: Int): Unit = TODO()
    fun simulationStatisticsRemaining(amount: Int): Unit = TODO()
}

enum class LogLevel {
    DEBUG,
    INFO,
    IMPORTANT
}
