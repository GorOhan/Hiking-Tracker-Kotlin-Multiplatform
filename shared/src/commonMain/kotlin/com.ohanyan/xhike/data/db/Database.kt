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
            hikeEntity.flightNumber,
            hikeEntity.missionName
        )
    }

    private fun mapDbEntity(
        flightNumber: Long,
        missionName: String,
    ): HikeEntity {
        return HikeEntity(
            flightNumber = flightNumber,
            missionName = missionName,
        )
    }

}