package me.monmcgt.code.util

class TimeCountUtil(val startTime: Long, val endTime: Long) {
    companion object {
        @JvmStatic
        fun newInstance(seconds: Int): TimeCountUtil {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + seconds * 1000
            return TimeCountUtil(startTime, endTime)
        }
    }

    fun getTimeLeft(): Long {
        return endTime - System.currentTimeMillis()
    }

    fun getTimePassed(): Long {
        return System.currentTimeMillis() - startTime
    }

    fun getTimeLeftPercent(): Double {
        return getTimeLeft() / getTimePassed().toDouble()
    }

    fun getTimePassedPercent(): Double {
        return getTimePassed() / endTime.toDouble()
    }

    fun getDifference(): Long {
        return endTime - startTime
    }

    fun isTimeLeft(): Boolean {
        return getTimeLeft() > 0
    }

    fun isTimeOver(): Boolean {
        return !isTimeLeft()
    }
}