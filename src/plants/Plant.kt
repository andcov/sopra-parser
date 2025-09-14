package plants

interface Plant {
    fun getHarvestEstimate() : Int
    fun getMoisture() : Int
    fun getSunlight() : Int
    fun getSelfPollination() : Boolean
}

enum class FieldPlant(val harvestEstimate : Int, val moisture : Int, val sunlight : Int, val selfPollination : Boolean, val sowingPeriod : Pair<Int, Int>, val harvestingPeriod : Pair<Int, Int>, val lateHarvest : Int) {
    POTATO(harvestEstimate = 1000000, moisture = 500, sunlight = 130, selfPollination = false, sowingPeriod = Pair(7, 10), harvestingPeriod = Pair(17, 20), lateHarvest = 0),
    WHEAT(harvestEstimate = 1500000, moisture = 450, sunlight = 90, selfPollination = true, sowingPeriod = Pair(19, 20), harvestingPeriod = Pair(11, 13), lateHarvest = 2),
    OAT(harvestEstimate = 1200000, moisture = 300, sunlight = 90, selfPollination = true, sowingPeriod = Pair(6, 6), harvestingPeriod = Pair(13, 16), lateHarvest = 2),
    PUMPKIN(harvestEstimate = 500000, moisture = 600, sunlight = 120, selfPollination = false, sowingPeriod = Pair(10, 12), harvestingPeriod = Pair(17, 20), lateHarvest = 0),
}

enum class PlantationPlant(val harvestEstimate : Int, val moisture : Int, val sunlight : Int, val selfPollination : Boolean, val cuttingPeriod : List<Pair<Int, Int>>, val mowingPeriod : Pair<Int, Int>, val bloomingPeriod : Pair<Int, Int>, val harvestingPeriod : List<Pair<Int, Int>>) {
    APPLE(harvestEstimate = 1700000, moisture = 100, sunlight = 50, selfPollination = false, cuttingPeriod = listOf(Pair(3, 4),
        Pair(21, 22)), mowingPeriod = Pair(11, 17), bloomingPeriod = Pair(8, 9), harvestingPeriod = listOf(Pair(17, 18), Pair(19, 19))),
    ALMOND(harvestEstimate = 800000, moisture = 400, sunlight = 130, selfPollination = false, cuttingPeriod = listOf(Pair(3, 4),
        Pair(21, 22)), mowingPeriod = Pair(11, 17), bloomingPeriod = Pair(4, 5), harvestingPeriod = listOf(Pair(16, 19))),
    CHERRY(harvestEstimate = 1200000, moisture = 150, sunlight = 120, selfPollination = false, cuttingPeriod = listOf(Pair(3, 4),
        Pair(21, 22)), mowingPeriod = Pair(11, 11), bloomingPeriod = Pair(8, 9), harvestingPeriod = listOf(Pair(13, 14))),
    GRAPE(harvestEstimate = 1200000, moisture = 250, sunlight = 150, selfPollination = true, cuttingPeriod = listOf(Pair(14, 14),
        Pair(15, 16)), mowingPeriod = Pair(7, 13), bloomingPeriod = Pair(12, 13), harvestingPeriod = listOf(Pair(17, 17))),
}
