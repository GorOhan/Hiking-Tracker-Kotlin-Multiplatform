package com.ohanyan.xhike.android.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.TestUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val testUseCase: TestUseCase,
) : ViewModel() {

    private val _toLoginScreen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toLoginScreen = _toLoginScreen.asStateFlow()

    private val _testString: MutableSharedFlow<Pair<String,String>> = MutableSharedFlow()
    val testString = _testString.asSharedFlow()



    init {
        viewModelScope.launch {
              testUseCase.invoke().forEach {
                _testString.emit(Pair("", "dfdf"))
            }

            testUseCase.insertToDB(HikeEntity(1L, "dffdf"))
        }
    }
}