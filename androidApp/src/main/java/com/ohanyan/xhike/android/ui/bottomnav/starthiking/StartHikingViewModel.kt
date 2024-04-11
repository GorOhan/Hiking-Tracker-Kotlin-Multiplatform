package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class StartHikingViewModel() : ViewModel() {

    private val _points: MutableStateFlow<List<GeoPoint>> = MutableStateFlow(emptyList())
    val points = _points.asStateFlow()


    val mockGeoPoints = listOf(
        GeoPoint(40.1855, 44.5165),
        GeoPoint(40.1857, 44.5163),
        GeoPoint(40.1859, 44.5161),
        GeoPoint(40.1861, 44.5159),
        GeoPoint(40.1863, 44.5157),
        GeoPoint(40.1865, 44.5155),
        GeoPoint(40.1867, 44.5153),
        GeoPoint (40.1869, 44.5151),
    GeoPoint(40.1871, 44.5149),
    GeoPoint(40.1873, 44.5147),
    GeoPoint(40.1875, 44.5145),
    GeoPoint(40.1877, 44.5143),
    GeoPoint(40.1879, 44.5141),
    GeoPoint(40.1881, 44.5139),
    GeoPoint(40.1883, 44.5137),
    GeoPoint(40.1885, 44.5135),
    GeoPoint (40.1887, 44.5133),
    GeoPoint(40.1889, 44.5131),
    GeoPoint(40.1891, 44.5129),
    GeoPoint(40.1893, 44.5127),
    GeoPoint(40.1895, 44.5125),
    GeoPoint(40.1897, 44.5123),
    GeoPoint(40.1899, 44.5121),
    GeoPoint(40.1901, 44.5119),
    GeoPoint(40.1903, 44.5117),
    GeoPoint(40.1905, 44.5115),
    GeoPoint(40.1907, 44.5113),
    GeoPoint(40.1909, 44.5111),
    GeoPoint(40.1911, 44.5109),
    GeoPoint (40.1913, 44.5107),
    GeoPoint (40.1915, 44.5105),
    GeoPoint (40.1917, 44.5103),
    GeoPoint (40.1919, 44.5101),
    GeoPoint(40.1921, 44.5099),
    GeoPoint(40.1923, 44.5097),
    GeoPoint(40.1925, 44.5095),
    GeoPoint(40.1927, 44.5093),
    GeoPoint(40.1929, 44.5091),
    GeoPoint(40.1931, 44.5089),
    GeoPoint(40.1933, 44.5087),
    GeoPoint(40.1935, 44.5085),
    GeoPoint(40.1937, 44.5083),
    GeoPoint(40.1939, 44.5081),
    GeoPoint(40.1941, 44.5079),
    GeoPoint(40.1943, 44.5077),
    GeoPoint(40.1945, 44.5075),
    GeoPoint(40.1947, 44.5073),
    GeoPoint (40.1949, 44.5071),
    GeoPoint (40.1951, 44.5069),
    GeoPoint (40.1953, 44.5067),
    GeoPoint(40.1955, 44.5065),
    GeoPoint(40.1957, 44.5063),
    GeoPoint(40.1959, 44.5061),
    GeoPoint(40.1961, 44.5059),
    GeoPoint(40.1963, 44.5057),
    GeoPoint(40.1965, 44.5055),
    GeoPoint(40.1967, 44.5053),
    GeoPoint(40.1969, 44.5051),
    GeoPoint(40.1971, 44.5049),
    GeoPoint(40.1973, 44.5047),
    GeoPoint(40.1975, 44.5045),
    GeoPoint(40.1977, 44.5043),
    GeoPoint(40.1979, 44.5041),
    GeoPoint(40.1981, 44.5039),
    GeoPoint(40.1983, 44.5037),
    GeoPoint(40.1985, 44.5035),
    GeoPoint(40.1987, 44.5033),
    GeoPoint(40.1989, 44.5031),
    GeoPoint(40.1991, 44.5029),
    GeoPoint(40.1993, 44.5027),
    GeoPoint(40.1995, 44.5025),
    GeoPoint(40.1997, 44.5023),
    GeoPoint(40.1999, 44.5021),
    GeoPoint(40.2001, 44.5019),
    GeoPoint(40.2003, 44.5017),
    GeoPoint(40.2005, 44.5015),
    GeoPoint(40.2007, 44.5013),
    GeoPoint(40.2009, 44.5011),
    GeoPoint(40.2011, 44.5009),
    GeoPoint(40.2013, 44.5007),
    GeoPoint(40.2015, 44.5005),
    GeoPoint(40.2017, 44.5003),
    GeoPoint(40.2019, 44.5001),
    GeoPoint(40.2021, 44.4999),
    GeoPoint(40.2023, 44.4997),
    GeoPoint(40.2025, 44.4995),
    GeoPoint(40.2027, 44.4993),
    GeoPoint(40.2029, 44.4991),
    GeoPoint(40.2031, 44.4989),
    GeoPoint(40.2033, 44.4987),
    GeoPoint(40.2035, 44.4985),
    GeoPoint(40.2037, 44.4983),
    GeoPoint(40.2039, 44.4981),
    GeoPoint(40.2041, 44.4979),
    GeoPoint(40.2043, 44.4977),
    GeoPoint(40.2045, 44.4975),
    GeoPoint(40.2047, 44.4973),
    GeoPoint(40.2049, 44.4971),
    GeoPoint(40.2051, 44.4969),
    GeoPoint(40.2053, 44.4967),)

    init {
        viewModelScope.launch {
            mockGeoPoints.forEachIndexed { index, point ->
                delay(200L)
                _points.emit(mockGeoPoints.subList(0, index + 1))
            }
        }
    }
}