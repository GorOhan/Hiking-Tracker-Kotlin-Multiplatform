package com.ohanyan.xhike.di

import com.ohanyan.xhike.TaskDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            TaskDatabase.Schema,
            get(),
            "tasks.db"
        )
    }
}