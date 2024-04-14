package com.ohanyan.xhike.di

import com.ohanyan.xhike.TaskDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(TaskDatabase.Schema, "tasks.db") }
}