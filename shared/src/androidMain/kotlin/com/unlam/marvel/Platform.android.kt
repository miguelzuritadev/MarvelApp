package com.unlam.marvel

import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import core.initializer.AppContextWrapper

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, AppContextWrapper.appContext!!, DB_NAME)
    }
}

actual fun resolveDataStorePath(): String {
    return AppContextWrapper.appContext!!.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}