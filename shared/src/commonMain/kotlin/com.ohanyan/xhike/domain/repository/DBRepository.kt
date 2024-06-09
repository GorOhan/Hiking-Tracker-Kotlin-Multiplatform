package com.ohanyan.xhike.domain.repository

import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.data.db.HikeEntity
import com.squareup.sqldelight.Query

interface DBRepository {
    fun insertHike(hikeEntity: HikeEntity)

    fun getAllHikes(): List<HikeEntity>

    fun updateHike(hikeEntity: HikeEntity)

    fun getHikeById(hikeId: Long): HikeEntity

    fun deleteHike(hikeId: Long)

    fun insertCurrentHike(currentHike: CurrentHike)

    fun getCurrentHike(listener: (CurrentHike?) -> Unit): Query.Listener

    fun removeCurrentHike()
}