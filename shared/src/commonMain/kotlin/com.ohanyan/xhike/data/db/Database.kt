package com.ohanyan.xhike.data.db

import com.ohanyan.xhike.HikeTable
import com.ohanyan.xhike.TaskDatabase
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class Database(sqlDriver: SqlDriver) {

    private val database = TaskDatabase(
        sqlDriver, hikeTableAdapter = HikeTable.Adapter(
            hikePointsAdapter = pointsAdapter,
            hikeDifficultyAdapter = hikeDiffAdapter
        )
    )
    private val dbQuery = database.taskDatabaseQueries


    internal fun updateHikeById(hikeEntity: HikeEntity) {
        dbQuery.updateHikeById(
            hikeEntity.hikeName,
            hikeEntity.hikeDescription,
            hikeEntity.hikeLengthInKm,
            hikeEntity.hikeDifficulty,
            hikeEntity.hikeRating,
            hikeEntity.hikeImage,
            hikeEntity.hikeTime,
            hikeEntity.hikeLocationLot,
            hikeEntity.hikeLocationLat,
            hikeEntity.hikeIsFavourite,
            hikeEntity.hikeId ?: 0,
        )
    }

    internal fun getAllHikes(): List<HikeEntity> {
        return dbQuery.getAllHikes(::mapDbEntity).executeAsList()
    }

    internal fun insertHike(hikeEntity: HikeEntity) {

        dbQuery.insertHike(
            hikeEntity.hikeId,
            hikeEntity.hikeName,
            hikeEntity.hikeDescription,
            hikeEntity.hikeLengthInKm,
            hikeEntity.hikeDifficulty,
            hikeEntity.hikeRating,
            hikeEntity.hikeImage,
            hikeEntity.hikeTime,
            hikeEntity.hikeLocationLot,
            hikeEntity.hikeLocationLat,
            hikeEntity.hikeIsFavourite,
            hikeEntity.hikePoints
        )
    }

    private fun mapDbEntity(
        hikeId: Long,
        hikeName: String,
        hikeDescription: String,
        hikeLengthInKm: Double,
        hikeDifficulty: HikeDifficulty,
        hikeRating: Double,
        hikeImage: String,
        hikeTime: String,
        hikeLocationLot: Double,
        hikeLocationLat: Double,
        hikeIsFavourite: Boolean,
        hikePoints: List<PointEntity>,
    ): HikeEntity {
        return HikeEntity(
            hikeId,
            hikeName,
            hikeDescription,
            hikeLengthInKm,
            hikeDifficulty,
            hikeRating,
            hikeImage,
            hikeTime,
            hikeLocationLot,
            hikeLocationLat,
            hikeIsFavourite,
            hikePoints
        )
    }

}

val hikeDiffAdapter = object : ColumnAdapter<HikeDifficulty, String> {
    override fun decode(databaseValue: String): HikeDifficulty =
        HikeDifficulty.valueOf(databaseValue)

    override fun encode(value: HikeDifficulty): String = value.name
}

val pointsAdapter = object : ColumnAdapter<List<PointEntity>, String> {
    override fun decode(databaseValue: String): List<PointEntity> =
        Json.decodeFromString<List<PointEntity>>(databaseValue)

    override fun encode(value: List<PointEntity>): String =
        Json.encodeToString(ListSerializer(PointEntity.serializer()), value)
}