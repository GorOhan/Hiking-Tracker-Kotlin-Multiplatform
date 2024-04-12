package com.ohanyan.xhike

import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.data.db.DatabaseDriverFactory
import com.ohanyan.xhike.domain.TestUseCase
import com.ohanyan.xhike.data.network.KtorExampleApi
import com.ohanyan.xhike.data.repository.DBRepositoryImpl
import com.ohanyan.xhike.data.repository.NetworkRepositoryImpl
import com.ohanyan.xhike.domain.repository.DBRepository
import com.ohanyan.xhike.domain.repository.NetworkRepository
import com.ohanyan.xhike.domain.usecases.GetHikesUseCase
import com.ohanyan.xhike.domain.usecases.InsertHikeInDbUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { TestUseCase(get(),get()) }
    factory { InsertHikeInDbUseCase(get()) }
    factory { GetHikesUseCase(get()) }
    single { Database(get()) }
    single { DatabaseDriverFactory(get()) }
    single<DBRepository> { DBRepositoryImpl(get()) }
}

val networkModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
    single { KtorExampleApi() }
}