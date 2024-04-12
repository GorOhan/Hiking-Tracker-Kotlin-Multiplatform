package com.ohanyan.xhike.data.db

import com.ohanyan.xhike.TaskDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = TaskDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.taskDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllHikes()
        }
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
            hikeEntity.hikeLocationLat
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
        hikeLocationLat: Double
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
            hikeLocationLat
        )
    }

}