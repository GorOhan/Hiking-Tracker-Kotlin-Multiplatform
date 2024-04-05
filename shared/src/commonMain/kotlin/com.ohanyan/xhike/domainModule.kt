package com.ohanyan.xhike

import com.ohanyan.xhike.domain.TestUseCase
import com.ohanyan.xhike.network.KtorExampleApi
import org.koin.dsl.module

val domainModule = module {
    single { TestUseCase(get()) }
}

val networkModule = module {
    single { KtorExampleApi() }
}