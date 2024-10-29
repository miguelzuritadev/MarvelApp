package com.unlam.marvel

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun initLogger()

const val DB_NAME = "marvel.db"

//SqlDelight
expect class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory): Database {
    return Database(driverFactory.createDriver())
}

//DataStore
expect fun resolveDataStorePath(): String
internal const val DATA_STORE_FILE_NAME = "marvel.preferences_pb"