package clouds

data class Cloud(val id : Int, val duration : Int, val location : Int, val amount : Int, val distance : Int)

// QUESTION: from what I can see, this doesn't allow us to create a Cloud with a provided id, correct?
//class Cloud private constructor(
//    val id: Int,
//    val duration: Int,
//    val location: Int,
//    val amount: Int,
//    val distance: Int
//) {
//    companion object {
//        private var maxId = 0
//
//        fun create(
//            duration: Int,
//            location: Int,
//            amount: Int,
//            distance: Int
//        ): Cloud {
//            maxId++
//            return Cloud(maxId, duration, location, amount, distance)
//        }
//
//        fun getMaxId(): Int = maxId
//    }
//}