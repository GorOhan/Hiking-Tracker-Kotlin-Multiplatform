package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.domain.repository.DBRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertCurrentHikeUseCase : KoinComponent {

    private val dbRepository: DBRepository by inject()

    operator fun invoke(currentHike: CurrentHike) {
        dbRepository.insertCurrentHike(currentHike)

    }
}