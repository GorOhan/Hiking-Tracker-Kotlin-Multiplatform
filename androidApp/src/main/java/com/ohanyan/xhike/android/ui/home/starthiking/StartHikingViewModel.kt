package com.ohanyan.xhike.android.ui.home.starthiking

import android.location.Location
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

    private val _hasPointChange: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasPointChange = _hasPointChange.asStateFlow()

    fun addPoint(point: Point) {
        viewModelScope.launch {
            if (_points.value.isNotEmpty()) {
                if (distanceInMeter(_points.value.last(), point) > 15) {
                    _points.value += point
                    _hasPointChange.emit(true)
                }else {
                    _hasPointChange.emit(false)
                }
            } else {
                _points.value += point
                _hasPointChange.emit(true)
            }
        }
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


private fun distanceInMeter(startPoint: Point, endPoint: Point): Float {
    var results = FloatArray(1)
    Location.distanceBetween(startPoint.latitude(),startPoint.longitude(),endPoint.latitude(),endPoint.longitude(),results)
    return results[0]
}