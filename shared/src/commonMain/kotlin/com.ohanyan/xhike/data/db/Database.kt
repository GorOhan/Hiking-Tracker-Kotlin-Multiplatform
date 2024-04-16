package com.ohanyan.xhike.data.db

import com.ohanyan.xhike.TaskDatabase
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

class Database(sqlDriver: SqlDriver) {

    private val database = TaskDatabase(sqlDriver)
    private val dbQuery = database.taskDatabaseQueries

//    internal fun clearDatabase() {
//        dbQuery.transaction {
//            dbQuery.removeAllHikes()
//        }
//    }

    internal fun updateHikeById(hikeEntity: HikeEntity) {
        dbQuery.updateHikeById(
            hikeEntity.hikeName,
            hikeEntity.hikeDescription,
            hikeEntity.hikeLengthInKm,
            hikeDiffAdapter.encode(hikeEntity.hikeDifficulty),
            hikeEntity.hikeRating,
            hikeEntity.hikeImage,
            hikeEntity.hikeTime,
            hikeEntity.hikeLocationLot,
            hikeEntity.hikeLocationLat,
            hikeEntity.hikeIsFavourite,
            hikeEntity.hikeId ?: 0
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
            hikeDiffAdapter.encode(hikeEntity.hikeDifficulty),
            hikeEntity.hikeRating,
            hikeEntity.hikeImage,
            hikeEntity.hikeTime,
            hikeEntity.hikeLocationLot,
            hikeEntity.hikeLocationLat,
            hikeEntity.hikeIsFavourite

        )
    }

    private fun mapDbEntity(
        hikeId: Long,
        hikeName: String,
        hikeDescription: String,
        hikeLengthInKm: Double,
        hikeDifficulty: String,
        hikeRating: Double,
        hikeImage: String,
        hikeTime: String,
        hikeLocationLot: Double,
        hikeLocationLat: Double,
        hikeIsFavourite: Boolean
    ): HikeEntity {
        return HikeEntity(
            hikeId,
            hikeName,
            hikeDescription,
            hikeLengthInKm,
            hikeDiffAdapter.decode(hikeDifficulty),
            hikeRating,
            hikeImage,
            hikeTime,
            hikeLocationLot,
            hikeLocationLat,
            hikeIsFavourite
        )
    }

}

val hikeDiffAdapter = object : ColumnAdapter<HikeDifficulty, String> {
    override fun decode(databaseValue: String): HikeDifficulty =
        HikeDifficulty.valueOf(databaseValue)

    override fun encode(value: HikeDifficulty): String = value.name
}