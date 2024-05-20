package com.ohanyan.xhike.android.ui.bottomnav.trails.trailsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikeByIdUseCase
import com.ohanyan.xhike.domain.usecases.UpdateHikeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrailSettingViewModel(
    private val getHikeByIdUseCase: GetHikeByIdUseCase,
    private val updateHikeUseCase: UpdateHikeUseCase
) : ViewModel() {

    private val _trail = MutableStateFlow(HikeEntity())
    val trail = _trail.asStateFlow()

    private val _onSaveChanges = MutableSharedFlow<Boolean>()
    val onSaveChanges = _onSaveChanges.asSharedFlow()

    init {

    }

    private fun updateHike(hikeEntity: HikeEntity) {
        updateHikeUseCase.invoke(hikeEntity)
        //  _hikes.value = getHikes()
    }

    fun getHikeById(hikeId: Int) {
        viewModelScope.launch {
            _trail.value = getHikeByIdUseCase.invoke(hikeId)
        }
    }

    fun onUpdateHike(hikeEntity: HikeEntity) {
        _trail.value = hikeEntity
    }

    fun saveChanges() {
        viewModelScope.launch {
            updateHikeUseCase.invoke(_trail.value)
            _onSaveChanges.emit(true)
        }
    }

}