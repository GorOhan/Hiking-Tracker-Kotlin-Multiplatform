package com.ohanyan.xhike.data.repository

import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository

class DBRepositoryImpl(
    private val database: Database
): DBRepository {
    override fun insertData(hikeEntity: HikeEntity) {
        database.insertHike(hikeEntity)
    }

    override fun getAllHikes(): List<HikeEntity> {
        return database.getAllHikes()
    }

    override fun updateHike(hikeEntity: HikeEntity) {
        database.updateHikeById(hikeEntity)
    }
}