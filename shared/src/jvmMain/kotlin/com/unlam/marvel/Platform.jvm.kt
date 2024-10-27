package com.unlam.marvel

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import java.io.File

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val isDebug = true
        val temporalDirectory = System.getProperty("java.io.tmpdir")
        val parentFolder = if (isDebug) {
            File(temporalDirectory)
        } else {
            File(System.getProperty("user.home") + "/MyFancyApp")
        }
        if (!parentFolder.exists()) {
            parentFolder.mkdirs()
        }
        val databasePath = if (isDebug) {
            File(temporalDirectory, DB_NAME)
        } else {
            File(parentFolder, DB_NAME)
        }
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")

        // Check if the database file exists before creating
        if (!databasePath.exists()) {
            Database.Schema.create(driver)
        }

        return driver
    }

}