package com.ohanyan.xhike.di

import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.domain.TestUseCase
import com.ohanyan.xhike.data.network.KtorApiImpl
import com.ohanyan.xhike.data.repository.DBRepositoryImpl
import com.ohanyan.xhike.data.repository.NetworkRepositoryImpl
import com.ohanyan.xhike.domain.repository.DBRepository
import com.ohanyan.xhike.domain.repository.NetworkRepository
import com.ohanyan.xhike.domain.usecases.DeleteHikeUseCase
import com.ohanyan.xhike.domain.usecases.GetHikeByIdUseCase
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import com.ohanyan.xhike.domain.usecases.UpdateHikeUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val useCasesModule = module {
    factory { TestUseCase() }
    factory { InsertHikeInDbUseCase() }
    factory { GetHikesUseCase() }
    factory { UpdateHikeUseCase() }
    factory { GetHikeByIdUseCase() }
    factory { DeleteHikeUseCase() }
}

val repositoryModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
    single { KtorApiImpl() }
    single<DBRepository> { DBRepositoryImpl(get()) }
    single { Database(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        useCasesModule,
        repositoryModule,
        platformModule
    )
}

fun initKoinForIos() = initKoin {}
