package com.unlam.marvel

import app.cash.sqldelight.db.SqlDriver
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = Database.Schema,
        name = DB_NAME
    )
}