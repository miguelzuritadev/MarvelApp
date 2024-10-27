package com.unlam.marvel

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun initLogger()

const val DB_NAME = "marvel.db"

expect class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory): Database {
    return Database(driverFactory.createDriver())
}