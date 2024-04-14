package com.ohanyan.xhike.di


import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.domain.TestUseCase
import com.ohanyan.xhike.data.network.KtorExampleApi
import com.ohanyan.xhike.data.repository.DBRepositoryImpl
import com.ohanyan.xhike.data.repository.NetworkRepositoryImpl
import com.ohanyan.xhike.domain.repository.DBRepository
import com.ohanyan.xhike.domain.repository.NetworkRepository
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val domainModule = module {
    factory { TestUseCase() }
    factory { InsertHikeInDbUseCase() }
    factory { GetHikesUseCase() }
    single { Database(get()) }
    single<DBRepository> { DBRepositoryImpl(get()) }
}

val networkModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
    single { KtorExampleApi() }
}



fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        domainModule,
        networkModule,
        platformModule
    )
}

fun initKoin() = initKoin {}