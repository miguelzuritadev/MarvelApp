package com.unlam.marvel

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual object Time {
    actual fun getTimeStamp(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()

    }
}