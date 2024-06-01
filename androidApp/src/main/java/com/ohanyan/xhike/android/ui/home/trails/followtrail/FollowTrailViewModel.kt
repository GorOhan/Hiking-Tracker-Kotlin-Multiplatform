package com.ohanyan.xhike.android.ui.home.trails.followtrail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.ohanyan.xhike.domain.usecases.GetHikeByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FollowTrailViewModel(
    private val getHikeByIdUseCase: GetHikeByIdUseCase,
) : ViewModel() {

    private val _loadStyle = MutableStateFlow<Boolean>(false)
    val loadStyle = _loadStyle.asStateFlow()

    private val _points: MutableStateFlow<List<Point>?> = MutableStateFlow(null)
    val points = _points.asStateFlow()

    private val _userLocation: MutableStateFlow<Point?> = MutableStateFlow(null)
    val userLocation = _userLocation.asStateFlow()
    fun getHikeById(hikeId: Int){
        viewModelScope.launch {
            _points.value = getHikeByIdUseCase.invoke(hikeId).hikePoints.map {
                Point.fromLngLat(it.pointLocationLot, it.pointLocationLat)
            }
            _loadStyle.value = true
        }
    }

    fun updateUserLocation(point: Point){
        viewModelScope.launch {
            _loadStyle.emit(true)
            _userLocation.emit(point)
        }
    }
}