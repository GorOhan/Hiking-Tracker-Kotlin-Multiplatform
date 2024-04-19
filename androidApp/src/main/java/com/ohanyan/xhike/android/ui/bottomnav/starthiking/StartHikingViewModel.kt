package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class StartHikingViewModel() : ViewModel() {

    private val _points: MutableStateFlow<List<GeoPoint>> = MutableStateFlow(emptyList())
    val points = _points.asStateFlow()

    init {
        viewModelScope.launch {}
    }

    fun addPoint(point: GeoPoint) {
        if (_points.value.isEmpty()) {
            _points.value = _points.value + point
            return
        }
        if (
            _points.value.last().distanceToAsDouble(point)
            > 5
        ) {
            println(" DISTANCE BETWEEN ${points.value.last().distanceToAsDouble(point)}")
            _points.value = _points.value + point

        }

    }

}