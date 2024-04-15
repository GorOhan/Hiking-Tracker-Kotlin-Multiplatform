package com.ohanyan.xhike.android.ui.bottomnav.trails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrailsViewModel(
    private val insertHikeInDbUseCase: InsertHikeInDbUseCase,
    private val getHikesUseCase: GetHikesUseCase,
) : ViewModel() {

    private val _currentHike = MutableStateFlow<HikeEntity>(HikeEntity())
    val currentHike = _currentHike.asStateFlow()

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
    }
}