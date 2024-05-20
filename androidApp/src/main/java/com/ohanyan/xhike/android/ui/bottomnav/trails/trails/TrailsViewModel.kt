package com.ohanyan.xhike.android.ui.bottomnav.trails.trails

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import com.ohanyan.xhike.domain.usecases.UpdateHikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrailsViewModel(
    private val insertHikeInDbUseCase: InsertHikeInDbUseCase,
    private val getHikesUseCase: GetHikesUseCase,
    private val updateHikeUseCase: UpdateHikeUseCase
) : ViewModel() {

    private val _hikes = MutableStateFlow<List<HikeEntity>>(listOf())
    val hikes = _hikes.asStateFlow()

    fun addHike(hikeEntity: HikeEntity) {
        insertHikeInDbUseCase.invoke(hikeEntity)
        _hikes.value = getHikesUseCase.invoke()
    }

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