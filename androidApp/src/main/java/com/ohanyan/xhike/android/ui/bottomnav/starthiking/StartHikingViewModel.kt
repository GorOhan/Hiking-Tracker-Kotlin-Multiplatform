package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class StartHikingViewModel() : ViewModel() {

    private val _points: MutableStateFlow<List<Point>> = MutableStateFlow(emptyList())
    val points = _points.asStateFlow()

    private val _startHiking: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startHiking = _startHiking.asStateFlow()

    init {
        viewModelScope.launch {}
    }

    fun addPoint(point: Point) {
            _points.value = _points.value + point
    }

    fun startHiking() {
        _startHiking.value = true
    }

}