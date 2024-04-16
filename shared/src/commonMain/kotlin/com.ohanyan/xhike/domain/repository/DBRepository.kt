package com.ohanyan.xhike.domain.repository

import com.ohanyan.xhike.data.db.HikeEntity

interface DBRepository {
    fun insertData(hikeEntity: HikeEntity)

    fun getAllHikes(): List<HikeEntity>

    fun updateHike(hikeEntity: HikeEntity)
}