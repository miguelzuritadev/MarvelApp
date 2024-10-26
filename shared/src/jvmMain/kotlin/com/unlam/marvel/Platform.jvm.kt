package com.unlam.marvel

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun initLogger() {
    Napier.base(DebugAntilog())
}