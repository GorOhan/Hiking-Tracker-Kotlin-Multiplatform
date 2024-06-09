package com.ohanyan.xhike.data.repository

import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository
import com.squareup.sqldelight.Query

class DBRepositoryImpl(
    private val database: Database
): DBRepository {
    override fun insertHike(hikeEntity: HikeEntity) {
        database.insertHike(hikeEntity)
    }

    override fun getAllHikes(): List<HikeEntity> {
        return database.getAllHikes()
    }

    override fun updateHike(hikeEntity: HikeEntity) {
        database.updateHikeById(hikeEntity)
    }

    override fun getHikeById(hikeId: Long): HikeEntity {
        return database.getHikeById(hikeId)
    }

    override fun deleteHike(hikeId: Long) {
        database.deleteHike(hikeId)
    }

    override fun insertCurrentHike(currentHike: CurrentHike) {
        database.insertCurrentHike(currentHike)
    }

    override fun getCurrentHike(listener: (CurrentHike?) -> Unit): Query.Listener {
        val query = database.dbQuery.getCurrentHike()

        val queryListener = object : Query.Listener {
            override fun queryResultsChanged() {
                listener(query.executeAsOneOrNull())
            }
        }

        query.addListener(queryListener)

        // Trigger initial load
        listener(query.executeAsOneOrNull())

        return queryListener
    }

    override fun removeCurrentHike() {
        database.removeCurrentHike()
    }
}