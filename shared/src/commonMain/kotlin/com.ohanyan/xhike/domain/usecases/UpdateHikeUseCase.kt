package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateHikeUseCase : KoinComponent {
    private val dbRepository: DBRepository by inject()

    fun invoke(
        hikeEntity: HikeEntity
    ) {
        dbRepository.updateHike(hikeEntity)
    }
}