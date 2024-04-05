package com.ohanyan.xhike.sqldelight

interface HikesDataStore  {
    fun getHikes(): List<Unit>
}

class HikesDataStoreImpl(
) : HikesDataStore {
    override fun getHikes(): List<Unit> {
        return emptyList()
    }
}