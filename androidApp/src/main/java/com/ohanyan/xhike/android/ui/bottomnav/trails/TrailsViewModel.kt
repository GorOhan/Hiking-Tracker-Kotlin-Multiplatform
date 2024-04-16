package com.ohanyan.xhike.android.ui.bottomnav.trails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import com.ohanyan.xhike.domain.usecases.UpdateHikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrailsViewModel(
    private val insertHikeInDbUseCase: InsertHikeInDbUseCase,
    private val getHikesUseCase: GetHikesUseCase,
    private val updateHikeUseCase: UpdateHikeUseCase
) : ViewModel() {

    private val _hikes = MutableStateFlow<List<HikeEntity>>(listOf())
    val hikes = _hikes.asStateFlow()

    init {
        viewModelScope.launch {
            _hikes.value = getHikes()
        }
    }
    private fun getHikes(): List<HikeEntity> {
        return getHikesUseCase.invoke()
    }

    fun addHike(hikeEntity: HikeEntity){
        insertHikeInDbUseCase.invoke(hikeEntity)
        _hikes.value = getHikes()
    }

    private fun updateHike(hikeEntity: HikeEntity){
        updateHikeUseCase.invoke(hikeEntity)
        _hikes.value = getHikes()
    }

    fun onFavouriteClick(hikeEntity: HikeEntity){
        val updatedHike = hikeEntity.copy(hikeIsFavourite = !hikeEntity.hikeIsFavourite)
        updateHike(updatedHike)
    }
}