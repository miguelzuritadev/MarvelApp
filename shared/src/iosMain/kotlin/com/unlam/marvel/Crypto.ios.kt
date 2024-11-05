package com.unlam.marvel

import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Foundation.NSUTF8StringEncoding
import platform.CommonCrypto.CC_MD5
import platform.CommonCrypto.CC_MD5_DIGEST_LENGTH
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned

actual object Crypto {
    actual fun md5(string: String): String {
        val data = (string as NSString).dataUsingEncoding(NSUTF8StringEncoding)!!
        val hash = UByteArray(CC_MD5_DIGEST_LENGTH)
        data.usePinned {
            CC_MD5(it.addressOf(0), data.length.convert(), hash.refTo(0))
        }
        return hash.joinToString("") { "%02x".format(it) }
    }
}