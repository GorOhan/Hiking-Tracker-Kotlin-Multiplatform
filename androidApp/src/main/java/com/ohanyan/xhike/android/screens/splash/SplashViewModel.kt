package com.ohanyan.xhike.android.screens.splash

import androidx.lifecycle.ViewModel
import com.ohanyan.xhike.domain.TestUseCase
import com.ohanyan.xhike.domain.usecases.RemoveCurrentHikeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel(
    private val testUseCase: TestUseCase,
    private val removeCurrentHikeUseCase: RemoveCurrentHikeUseCase
) : ViewModel() {

    init {
        removeCurrentHikeUseCase()
    }

    private val _toLoginScreen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toLoginScreen = _toLoginScreen.asStateFlow()

    private val _testString: MutableSharedFlow<Pair<String,String>> = MutableSharedFlow()
    val testString = _testString.asSharedFlow()

}