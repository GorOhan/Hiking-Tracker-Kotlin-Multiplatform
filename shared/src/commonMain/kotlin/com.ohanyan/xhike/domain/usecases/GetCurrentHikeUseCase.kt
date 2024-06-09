package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.domain.repository.DBRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCurrentHikeUseCase : KoinComponent {

    private val dbRepository: DBRepository by inject()
    operator fun invoke():Flow<CurrentHike?> = callbackFlow {
        dbRepository.getCurrentHike {
            trySend(it)
        }

        awaitClose {
            dbRepository.removeCurrentHike()
        }
    }

}