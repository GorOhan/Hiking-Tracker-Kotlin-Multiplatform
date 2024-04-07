package com.ohanyan.xhike.domain.repository

import com.ohanyan.xhike.data.db.HikeEntity

interface DBRepository {
    fun insertData(hikeEntity: HikeEntity): Unit
}