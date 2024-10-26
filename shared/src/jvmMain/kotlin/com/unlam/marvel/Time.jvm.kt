package com.unlam.marvel

actual object Time {
    actual fun getTimeStamp(): Long = System.currentTimeMillis()
}