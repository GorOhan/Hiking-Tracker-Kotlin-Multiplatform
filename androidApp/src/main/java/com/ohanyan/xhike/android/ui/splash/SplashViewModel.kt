package com.ohanyan.xhike.android.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.TestUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val testUseCase: TestUseCase,
) : ViewModel() {

    private val _toLoginScreen: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val toLoginScreen = _toLoginScreen.asSharedFlow()

    private val _testString: MutableSharedFlow<String> = MutableSharedFlow()
    val testString = _testString.asSharedFlow()


    init {
        viewModelScope.launch {
            delay(2000)
            _toLoginScreen.emit(true)
            testUseCase.invoke().forEach {
                _testString.emit(it.missionName)
            }

            testUseCase.insertToDB(HikeEntity(1L, "dffdf"))
        }
    }
}