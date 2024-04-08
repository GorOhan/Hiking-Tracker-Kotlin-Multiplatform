package com.ohanyan.xhike.android.ui.home

import androidx.lifecycle.ViewModel
import com.ohanyan.xhike.domain.TestUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeViewModel(
    private val testUseCase: TestUseCase,
) : ViewModel() {

    private val _toLoginScreen: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val toLoginScreen = _toLoginScreen.asSharedFlow()

    private val _testString: MutableSharedFlow<Pair<String,String>> = MutableSharedFlow()
    val testString = _testString.asSharedFlow()


}