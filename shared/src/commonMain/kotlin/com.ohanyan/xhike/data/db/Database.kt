package com.ohanyan.xhike.data.db

import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.HikeTable
import com.ohanyan.xhike.TaskDatabase
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class Database(sqlDriver: SqlDriver) {

    private val database = TaskDatabase(
        driver = sqlDriver,
        hikeTableAdapter = HikeTable.Adapter(
            hikePointsAdapter = pointsAdapter,
            hikeDifficultyAdapter = hikeDiffAdapter
        ),
        currentHikeAdapter = CurrentHike.Adapter(
            hikePointsAdapter = pointsAdapter
        )
    )

    val dbQuery = database.taskDatabaseQueries

    internal fun insertCurrentHike(currentHike: CurrentHike) {
        dbQuery.insertCurrentHike(
            currentHike.hikeId,
            currentHike.hikePoints
        )
    }

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

    internal fun getHikeById(hikeId: Long): HikeEntity {
        return dbQuery.getHikeById(hikeId, ::mapDbEntity).executeAsOne()
    }

    internal fun deleteHike(hikeId: Long) {
        dbQuery.deleteHike(hikeId)
    }

    internal fun removeCurrentHike(){
        dbQuery.removeCurrentHike()
    }
    internal fun getCurrentHike(listener: (CurrentHike) -> Unit): Query.Listener {
        val query = dbQuery.getCurrentHike()

        val queryListener = object : Query.Listener {
            override fun queryResultsChanged() {
                listener(query.executeAsOne())
            }
        }

        query.addListener(queryListener)

        // Trigger initial load
        listener(query.executeAsOne())

        return queryListener
    }



    private fun mapDbEntity(
        hikeId: Long,
        hikeName: String,
        hikeDescription: String,
        hikeLengthInKm: Double,
        hikeDifficulty: HikeDifficulty,
        hikeRating: Double,
        hikeImage: String,
        hikeTime: Double,
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