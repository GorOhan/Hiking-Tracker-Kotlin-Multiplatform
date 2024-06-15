package com.ohanyan.xhike.android.screens.home.trails.singletrail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.usecases.GetHikeByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SingleTrailViewModel(
    private val getHikeByIdUseCase: GetHikeByIdUseCase,
) : ViewModel() {

    private val _hike = MutableStateFlow<HikeEntity?>(null)
    val hike = _hike.asStateFlow()

    private val _points: MutableStateFlow<List<Point>?> = MutableStateFlow(null)
    val points = _points.asStateFlow()

    fun getHikeById(hikeId: Int) {
        viewModelScope.launch {
            _points.value = getHikeByIdUseCase.invoke(hikeId).hikePoints.map {
                Point.fromLngLat(it.pointLocationLot, it.pointLocationLat)
            }
            _hike.value = getHikeByIdUseCase(hikeId)
        }
    }
}