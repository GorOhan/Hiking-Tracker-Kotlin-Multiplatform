package com.ohanyan.xhike.data.db

data class HikeEntity(
    val hikeId: Long? = null,
    val hikeName: String = "Hike Name",
    val hikeDescription: String = "Hike Description",
    val hikeLengthInKm: Double = 0.0,
    val hikeDifficulty: HikeDifficulty = HikeDifficulty.HARD,
    val hikeRating: Double = 0.0,
    val hikeImage: String = "",
    val hikeTime: String = "Hike Time",
    val hikeLocationLot: Double = 0.0,
    val hikeLocationLat: Double = 0.0,
    val hikeIsFavourite: Boolean = false,
)

enum class HikeDifficulty(val value: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}

enum class HikeRate(val value: Double) {
    BAD(1.0),
    OK(2.0),
    GOOD(3.0),
    GREAT(4.0),
    EXCELLENT(5.0)
}