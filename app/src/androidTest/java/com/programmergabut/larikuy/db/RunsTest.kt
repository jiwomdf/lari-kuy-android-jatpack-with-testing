package com.programmergabut.larikuy.db

data class RunsTest(
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMater: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0,
)