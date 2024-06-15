package com.ohanyan.xhike.android.screens.home.trails.trails

import androidx.lifecycle.ViewModel
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.UpdateHikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrailsViewModel(
    private val getHikesUseCase: GetHikesUseCase,
    private val updateHikeUseCase: UpdateHikeUseCase
) : ViewModel() {

    private val _hikes = MutableStateFlow<List<HikeEntity>>(listOf())
    val hikes = _hikes.asStateFlow()

    private fun updateHike(hikeEntity: HikeEntity) {
        updateHikeUseCase.invoke(hikeEntity)
        _hikes.value = getHikesUseCase.invoke()
    }

    fun onFavouriteClick(hikeEntity: HikeEntity) {
        val updatedHike = hikeEntity.copy(hikeIsFavourite = !hikeEntity.hikeIsFavourite)
        updateHike(updatedHike)
    }

    fun getHikes() {
        _hikes.value = getHikesUseCase.invoke().sortedBy { !it.hikeIsFavourite }
    }
}