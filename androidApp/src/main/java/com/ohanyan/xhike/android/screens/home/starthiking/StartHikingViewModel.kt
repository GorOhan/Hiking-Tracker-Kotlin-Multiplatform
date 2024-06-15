package com.ohanyan.xhike.android.screens.home.starthiking

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.data.db.PointEntity
import com.ohanyan.xhike.domain.usecases.GetCurrentHikeUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import com.ohanyan.xhike.domain.usecases.RemoveCurrentHikeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartHikingViewModel(
    private val insertHikeInDbUseCase: InsertHikeInDbUseCase,
    private val removeCurrentHikeUseCase: RemoveCurrentHikeUseCase,
    private val getCurrentHikeUseCase: GetCurrentHikeUseCase,
) : ViewModel() {

    private val _points: MutableStateFlow<List<Point>> = MutableStateFlow(emptyList())
    val points = _points.asStateFlow()

    private val _startHiking: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startHiking = _startHiking.asStateFlow()

    private val _hasPointChange: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val hasPointChange = _hasPointChange.asStateFlow()

    fun startHiking() {
        _startHiking.value = true
        startListen()
    }

    private fun startListen() {
        viewModelScope.launch {
            getCurrentHikeUseCase().collect { currentHike ->
                currentHike?.let {
                    val lastPoint =
                        it.hikePoints.map {
                            Point.fromLngLat(
                                it.pointLocationLot,
                                it.pointLocationLat
                            )
                        }
                            .last()
                    viewModelScope.launch {
                        if (_points.value.isNotEmpty()) {
                            if (distanceInMeter(_points.value.last(), lastPoint) > 15) {
                                _points.value = it.hikePoints.map {
                                    Point.fromLngLat(
                                        it.pointLocationLot,
                                        it.pointLocationLat
                                    )
                                }
                                _hasPointChange.emit(true)
                            } else {
                                _hasPointChange.emit(false)
                            }
                        } else {
                            _points.value = it.hikePoints.map {
                                Point.fromLngLat(
                                    it.pointLocationLot,
                                    it.pointLocationLat
                                )
                            }
                            _hasPointChange.emit(true)
                        }
                    }
                }
            }
        }
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

        removeCurrentHikeUseCase()
    }

    fun drawRoute() {
        viewModelScope.launch {
            _hasPointChange.value = true
            delay(200L)
            _hasPointChange.value = false
        }
    }
}


private fun distanceInMeter(startPoint: Point, endPoint: Point): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        startPoint.latitude(),
        startPoint.longitude(),
        endPoint.latitude(),
        endPoint.longitude(),
        results
    )
    return results[0]
}