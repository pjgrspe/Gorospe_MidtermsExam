package ph.edu.auf.gorospe.patrickjason.gorospe_midtermsexam.models

import java.util.Date

data class TasksModel(
    val taskNo: Int,
    val taskDescription: String,
    val taskDateTime: Date
) {
    companion object {
        private var counter = 0
        fun getNextTaskNo(): Int {
            counter += 1
            return counter
        }
    }
}