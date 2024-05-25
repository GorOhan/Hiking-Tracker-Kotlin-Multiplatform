package com.ohanyan.xhike.android.ui.main.starthiking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.data.db.PointEntity
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartHikingViewModel(
    private val insertHikeInDbUseCase: InsertHikeInDbUseCase
) : ViewModel() {

    private val _points: MutableStateFlow<List<Point>> = MutableStateFlow(emptyList())
    val points = _points.asStateFlow()

    private val _startHiking: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startHiking = _startHiking.asStateFlow()

    fun addPoint(point: Point) {
        _points.value += point
    }

    fun startHiking() {
        _startHiking.value = true
    }

    fun finishHike() {
        _startHiking.value = false
        viewModelScope.launch {
            insertHikeInDbUseCase(
               hikeEntity = HikeEntity(
                   hikePoints = points.value.map {
                          PointEntity(
                            pointLocationLot = it.longitude(),
                            pointLocationLat = it.latitude()
                          )
                   }
               )
            )
        }
    }

}