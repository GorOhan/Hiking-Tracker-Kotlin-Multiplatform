package com.ohanyan.xhike.data.db
import kotlinx.serialization.Serializable

@Serializable
data class HikeEntity(
    val hikeId: Long? = null,
    val hikeName: String = "Hike Name",
    val hikeDescription: String = "Hike Description",
    val hikeLengthInKm: Double = 0.0,
    val hikeDifficulty: HikeDifficulty = HikeDifficulty.HARD,
    val hikeRating: Double = 0.0,
    val hikeImage: String = "",
    val hikeTime: Double = 0.0,
    val hikeLocationLot: Double = 0.0,
    val hikeLocationLat: Double = 0.0,
    val hikeIsFavourite: Boolean = false,
    val hikePoints: List<PointEntity> = emptyList()
)

@Serializable
data class PointEntity(
    val pointLocationLot: Double = 0.0,
    val pointLocationLat: Double = 0.0,
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

enum class HikeTime(val value: Double) {
    SHORT(1.0),
    BASE(2.0),
    MEDIUM(3.0),
    LONG(4.0),
    VERY_LONG(5.0)
}